package com.sky.service;

import com.sky.dto.ChatDTO;
import com.sky.dto.SessionDTO;
import com.sky.vo.ChatVO;
import com.sky.vo.SessionVO;


public interface ChatSessionService {
    /**
     * 创建ai聊天会话
     */
    SessionVO createSession(SessionDTO sessionDTO);

    /**
     * 删除ai聊天会话
     */
    void deleteSession(Integer sessionId);
}
