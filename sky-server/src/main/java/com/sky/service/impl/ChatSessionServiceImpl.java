package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ChatDTO;
import com.sky.dto.SessionDTO;
import com.sky.entity.ChatMessage;
import com.sky.entity.ChatSession;
import com.sky.mapper.ChatMessageMapper;
import com.sky.mapper.ChatSessionMapper;
import com.sky.service.ChatSessionService;
import com.sky.vo.ChatListVO;
import com.sky.vo.ChatVO;
import com.sky.vo.SessionVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class ChatSessionServiceImpl implements ChatSessionService {

    @Autowired
    private ChatSessionMapper chatSessionMapper;

    @Autowired
    private ChatMessageMapper chatMessageMapper;

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
    @Transactional
    public void deleteSession(Integer sessionId){

        chatSessionMapper.deleteById(sessionId);
        chatMessageMapper.deleteBySessionId(sessionId);

    }

    /**
     * 获取会话列表记录
     */
    @Transactional
    public List<ChatListVO> list(){
        Long adminId = BaseContext.getCurrentId();
        List<ChatListVO> chatListVOList = chatSessionMapper.getByAdminId(adminId);
        chatListVOList.forEach(item -> {
            if (item.getLastMessage() == null) {
                item.setLastMessage("");
            }
        });
        return chatListVOList;
    }
}
