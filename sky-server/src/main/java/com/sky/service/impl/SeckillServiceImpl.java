package com.sky.service.impl;
import com.sky.entity.SeckillOrder;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.SeckillOrderMapper;
import com.sky.result.Result;
import com.sky.service.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

@Service
@Slf4j
public class SeckillServiceImpl implements SeckillService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private DefaultRedisScript<Long> seckillScript;
    @Autowired
    private SeckillOrderMapper seckillOrderMapper;

    public Result<String> executeSeckill(Long setmealId, Long userId) {
        // 1. 定义 Redis 的 Key
        String stockKey = "seckill:stock:" + setmealId;
        String userKey = "seckill:user:" + setmealId;

        // 2. 执行 Lua 脚本
        // 参数：脚本, KEYS列表, ARGV列表
        Long result = stringRedisTemplate.execute(
                seckillScript,
                Arrays.asList(stockKey, userKey),
                userId.toString(), "1"
        );

        // 3. 根据脚本返回结果判断
        if (result == -1) {
            return Result.error("您已经参加过活动了，请勿重复下单");
        }
        if (result == 0) {
            return Result.error("手慢了，库存不足");
        }

        // 4. 走到这里说明 result == 1，Redis 预扣库存成功！
        // 因为没有消息队列，我们现在直接在这里操作数据库生成订单
        // 注意：由于已经通过 Redis 拦截了绝大部分请求，此时进来的压力 MySQL 是可以承受的
        try {
            createOrder(setmealId, userId);
        } catch (Exception e) {
            log.error("秒杀订单写入失败，用户ID: {}, 套餐ID: {}, 错误信息: {}", userId, setmealId, e.getMessage());
            // 注意：这里如果不做库存回滚，会出现“少卖”
            return Result.error("下单失败，请联系客服");
        }

        return Result.success("秒杀成功，订单处理中");
    }

    private void createOrder(Long setmealId, Long userId) {
        // 1. 查询秒杀活动信息（获取价格和活动ID）
        BigDecimal price = seckillOrderMapper.getSeckillPriceBySetmealId(setmealId);
        Long seckillId = seckillOrderMapper.getSeckillIdBySetmealId(setmealId);

        // 2. 构造订单对象
        SeckillOrder seckillOrder = SeckillOrder.builder()
                .orderNumber(String.valueOf(System.currentTimeMillis()) + userId) // 简单生成订单号
                .userId(userId)
                .setmealId(setmealId)
                .seckillId(seckillId)
                .amount(price)
                .status(0) // 待支付
                .orderTime(LocalDateTime.now())
                .build();

        // 3. 插入数据库
        seckillOrderMapper.insert(seckillOrder);
        log.info("秒杀订单入库成功：订单号 {}", seckillOrder.getOrderNumber());
    }

    @Override
    public void prepareSeckillData(Long setmealId, Integer stock) {
        String stockKey = "seckill:stock:" + setmealId;
        String userKey = "seckill:user:" + setmealId;

        // 1. 设置库存
        stringRedisTemplate.opsForValue().set(stockKey, stock.toString());
        // 2. 清空之前的抢购记录，确保可以重复测试
        stringRedisTemplate.delete(userKey);

        log.info("秒杀活动预热成功！套餐ID: {}, 初始库存: {}", setmealId, stock);
    }
}