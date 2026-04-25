package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.service.SeckillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/seckill")
@Api(tags = "管理端秒杀设置")
public class AdminSeckillController {

    @Autowired
    private SeckillService seckillService;

    @PostMapping("/prepare")
    @ApiOperation("预热秒杀库存")
    public Result<String> prepare(@RequestParam Long setmealId, @RequestParam Integer stock) {
        seckillService.prepareSeckillData(setmealId, stock);
        return Result.success("预热成功");
    }
}