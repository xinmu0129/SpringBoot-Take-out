package com.sky.service;

import com.sky.dto.SessionDTO;
import com.sky.vo.SessionVO;


public interface ChatSessionService {
    /**
     * 创建ai聊天会话
     */
    SessionVO createSession(SessionDTO sessionDTO);

}
