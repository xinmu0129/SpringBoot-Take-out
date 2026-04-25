package com.sky.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeckillOrder implements Serializable {
    private Long id;
    private String orderNumber; // 订单号
    private Long userId;        // 用户ID
    private Long setmealId;     // 套餐ID
    private Long seckillId;     // 秒杀活动ID
    private BigDecimal amount;  // 支付金额
    private Integer status;     // 状态 0:未支付, 1:已支付, 2:已核销, 3:已取消
    private LocalDateTime orderTime; // 下单时间
}