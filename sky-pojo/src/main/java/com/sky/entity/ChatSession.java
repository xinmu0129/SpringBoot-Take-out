package com.sky.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * AI聊天会话实体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatSession implements Serializable {

    private static final long serialVersionUID = 1L;

    // 主键ID (对应数据库 bigint)
    private Long id;

    // 会话标题 (对应数据库 varchar)
    private String chatTitle;

    // 创建该会话的管理员ID (对应数据库 bigint)
    private Long adminId;

    // 创建时间 (对应数据库 datetime)
    private LocalDateTime createTime;

    // 最后消息时间/更新时间 (对应数据库 datetime)
    private LocalDateTime updateTime;

    // 逻辑删除：0未删，1已删 (对应数据库 tinyint)
    // 在苍穹外卖中，通常用 Integer 或简单的 int 来映射 tinyint
    private Integer isDeleted;
}
