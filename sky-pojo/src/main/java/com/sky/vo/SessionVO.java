package com.sky.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SessionVO {
    private Long sessionId;
    private String title;
}