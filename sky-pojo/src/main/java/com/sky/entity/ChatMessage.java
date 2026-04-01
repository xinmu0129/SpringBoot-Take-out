package com.sky.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * AI聊天消息明细实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    // 所属会话ID
    private Long sessionId;

    // 角色：user(用户), assistant(AI)
    private String role;

    // 消息内容
    private String content;

    // 发送时间
    private LocalDateTime createTime;

    //时间戳
    private Long timestamps;
}