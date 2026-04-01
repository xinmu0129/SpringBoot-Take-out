package com.sky.ai.tool;

import com.sky.service.OrderService;
import com.sky.vo.OrderVO;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderTool {

    @Autowired
    private OrderService orderService;

    @Tool("根据订单ID查询订单详情，例如：123")
    public String getOrderById(Long id) {

        OrderVO orderVO = orderService.getOrderDetail(id);
        if (orderVO == null) {
            return "未查询到该订单";
        }
        // 👉 拼成“人话”返回（非常重要）
        return "订单ID：" + orderVO.getId() +
                "，金额：" + orderVO.getAmount() +
                "元，状态：" + getStatusDesc(orderVO.getStatus());
    }

    // 👉 状态转中文（可选优化）
    private String getStatusDesc(Integer status) {
        switch (status) {
            case 1: return "待付款";
            case 2: return "待接单";
            case 3: return "已接单";
            case 4: return "派送中";
            case 5: return "已完成";
            case 6: return "已取消";
            default: return "未知状态";
        }
    }

    //TODO 完成更多AI功能
}