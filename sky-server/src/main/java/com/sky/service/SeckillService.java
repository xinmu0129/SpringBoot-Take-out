package com.sky.service;

import com.sky.result.Result;

public interface SeckillService {
    /**
     * 执行秒杀下单逻辑
     * @param setmealId 套餐ID
     * @return 结果信息
     */
    Result<String> executeSeckill(Long setmealId, Long userId);

    void prepareSeckillData(Long setmealId, Integer stock);
}