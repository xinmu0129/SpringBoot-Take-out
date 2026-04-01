package com.sky.service.impl;

import com.sky.dto.ChatDTO;
import com.sky.entity.ChatMessage;
import com.sky.mapper.ChatMessageMapper;
import com.sky.mapper.ChatSessionMapper;
import com.sky.service.AdminChatAssistant;
import com.sky.service.ChatMessageService;
import com.sky.vo.ChatHistoryVO;
import com.sky.vo.ChatVO;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

        //保存用户发送的信息
        ChatMessage chatMessage = ChatMessage.builder()
                .sessionId(sessionId)
                .role("user")
                .content(message)
                .createTime(LocalDateTime.now())
                .timestamps(System.currentTimeMillis())
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
                .timestamps(System.currentTimeMillis())
                .build();
        chatMessageMapper.insert(aiReplyMessage);

        //更新会话时间

        chatSessionMapper.updateTimeBySeesionId(sessionId , LocalDateTime.now());

        //封装返回

        ChatVO chatVO = ChatVO.builder()
                .sessionId(sessionId)
                .messageId(String.valueOf(aiReplyMessage.getId()))
                .timestamp(System.currentTimeMillis())
                .botReply(aiReplyContent)
                .build();
        return chatVO;
    }

    /**
     * 获取历史记录
     */
    public ChatHistoryVO getHistory(Integer sessionId) {
        // 1. 获取数据库原始实体列表（包含所有字段）
        List<ChatMessage> messageList = chatMessageMapper.getBySessionId(sessionId);

        // 2. 转换为精简的 VO 列表，只保留文档要求的 3 个字段
        List<ChatHistoryVO.MessageItemVO> messageListRes = messageList.stream()
                .map(message -> ChatHistoryVO.MessageItemVO.builder()
                        .role(message.getRole())
                        .content(message.getContent())
                        .timestamps(message.getTimestamps()) // 直接取数据库存好的时间戳
                        .build())
                .collect(Collectors.toList());

        // 3. 构造并返回
        return ChatHistoryVO.builder()
                .sessionId(Long.valueOf(sessionId))
                .messages(messageListRes)
                .build();
    }
}
