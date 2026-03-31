package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.SessionDTO;
import com.sky.entity.ChatSession;
import com.sky.mapper.ChatSessionMapper;
import com.sky.service.ChatSessionService;
import com.sky.vo.SessionVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class ChatSessionServiceImpl implements ChatSessionService {

    @Autowired
    private ChatSessionMapper chatSessionMapper;

    /**
     * 创建ai聊天会话
     */
    public SessionVO createSession(SessionDTO sessionDTO)
    {
        log.info("创建会话：{}", sessionDTO);
        ChatSession chatSession = new ChatSession();
        chatSession.setChatTitle(sessionDTO.getTitle());
        Long userId = BaseContext.getCurrentId();
        chatSession.setAdminId(userId);
        chatSession.setCreateTime(LocalDateTime.now());
        chatSession.setUpdateTime(LocalDateTime.now());

        chatSessionMapper.insert(chatSession);

        SessionVO sessionVO = SessionVO.builder()
                .sessionId(chatSession.getId())
                .title(chatSession.getChatTitle())
                .build();

        return sessionVO;
    }

    /**
     * 删除ai聊天会话
     */
    public void deleteSession(Integer sessionId){
        chatSessionMapper.deleteById(sessionId);
    }
}
