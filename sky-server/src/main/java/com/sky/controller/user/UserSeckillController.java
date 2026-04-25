package com.sky.controller.user;

import com.sky.result.Result;
import com.sky.service.SeckillService;
import com.sky.context.BaseContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/seckill")
@Api(tags = "用户端秒杀")
public class UserSeckillController {

    @Autowired
    private SeckillService seckillService;

    @PostMapping("/{setmealId}")
    @ApiOperation("参与秒杀抢购")
    public Result<String> seckill(@PathVariable Long setmealId) {
        // 从 ThreadLocal 中获取当前用户ID（苍穹外卖的标准做法）
        Long userId = com.sky.context.BaseContext.getCurrentId();

        return seckillService.executeSeckill(setmealId, userId);
    }
}