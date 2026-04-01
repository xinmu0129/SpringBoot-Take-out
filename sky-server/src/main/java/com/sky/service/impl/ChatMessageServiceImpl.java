package com.sky.service.impl;

import com.sky.dto.ChatDTO;
import com.sky.entity.ChatMessage;
import com.sky.mapper.ChatMessageMapper;
import com.sky.mapper.ChatSessionMapper;
import com.sky.service.AdminChatAssistant;
import com.sky.service.ChatMessageService;
import com.sky.vo.ChatVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class ChatMessageServiceImpl implements ChatMessageService {

    @Autowired
    private ChatMessageMapper chatMessageMapper;

    @Autowired
    private AdminChatAssistant adminChatAssistant;

    @Autowired
    private ChatSessionMapper chatSessionMapper;

    /**
     * ai助手聊天
     */
    public ChatVO send(ChatDTO chatDTO){
        Long sessionId = chatDTO.getSessionId();
        String message = chatDTO.getMessage();
        Long timestamp = chatDTO.getTimestamp();
        //保存用户发送的信息
        ChatMessage chatMessage = ChatMessage.builder()
                .sessionId(sessionId)
                .role("user")
                .content(message)
                .createTime(LocalDateTime.now())
                .build();
        chatMessageMapper.insert(chatMessage);

        //调用ai 将信息发给大模型

        log.info("开始调用大模型，SessionID: {}, 发送内容: {}", sessionId, message);
        String aiReplyContent = adminChatAssistant.chat(sessionId, message);
        log.info("大模型回复完成: {}", aiReplyContent);

        //保存ai发送的信息，入库

        ChatMessage aiReplyMessage = ChatMessage.builder()
                .sessionId(sessionId)
                .role("assistant")
                .content(aiReplyContent)
                .createTime(LocalDateTime.now())
                .build();
        chatMessageMapper.insert(aiReplyMessage);

        //更新会话时间

        chatSessionMapper.updateTimeBySeesionId(sessionId , LocalDateTime.now());

        //封装返回

        long timestampRes = System.currentTimeMillis();

        ChatVO chatVO = ChatVO.builder()
                .sessionId(sessionId)
                .messageId(String.valueOf(aiReplyMessage.getId()))
                .timestamp(timestampRes)
                .botReply(aiReplyContent)
                .build();
        return chatVO;
    }
}
