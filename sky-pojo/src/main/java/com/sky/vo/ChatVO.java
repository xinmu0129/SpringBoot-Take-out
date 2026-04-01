package com.sky.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatVO {
    private Long sessionId;
    private String botReply;
    private Long timestamp;
    private String messageId;
}
