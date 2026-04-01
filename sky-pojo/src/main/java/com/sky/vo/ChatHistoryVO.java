package com.sky.vo;

import com.sky.entity.ChatMessage;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ChatHistoryVO {
    private Long sessionId;
    private List<MessageItemVO> messages; // 这里的列表元素必须是专门的 VO

    @Data
    @Builder
    public static class MessageItemVO {
        private String role;
        private String content;
        private Long timestamps; // 只要这三个字段
    }
}
