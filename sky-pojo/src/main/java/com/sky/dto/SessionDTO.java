package com.sky.dto;

import lombok.Data;

@Data
public class SessionDTO {
    private String title;
    private Long userId; // 管理员ID
}
