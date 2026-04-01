package com.sky.dto;

import lombok.Data;

@Data
public class ChatDTO {
    private Long sessionId;
    private String message;
    private Long timestamp;
}
