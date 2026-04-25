package com.sky.mapper;

import com.sky.entity.SeckillOrder;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;

@Mapper
public interface SeckillOrderMapper {

    @Insert("insert into order_seckill (order_number, user_id, setmeal_id, seckill_id, amount, status, order_time) " +
            "values (#{orderNumber}, #{userId}, #{setmealId}, #{seckillId}, #{amount}, #{status}, #{orderTime})")
    void insert(SeckillOrder seckillOrder);

    // 顺便写一个查询活动信息的，用于获取秒杀价
    @Select("select seckill_price from setmeal_seckill where setmeal_id = #{setmealId} and status = 1")
    BigDecimal getSeckillPriceBySetmealId(Long setmealId);

    @Select("select id from setmeal_seckill where setmeal_id = #{setmealId} and status = 1")
    Long getSeckillIdBySetmealId(Long setmealId);
}