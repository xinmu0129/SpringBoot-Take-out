package com.sky.vo;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ChatListVO {
    private Long sessionId;
    private String sessionTitle;
    private String lastMessage;;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
