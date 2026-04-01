package com.sky.service;

import com.sky.dto.ChatDTO;
import com.sky.vo.ChatHistoryVO;
import com.sky.vo.ChatVO;

public interface ChatMessageService {

    /**
     * ai助手聊天
     */
    ChatVO send(ChatDTO chatDTO);

    /**
     * 获取历史记录
     */
    ChatHistoryVO getHistory(Integer sessionId);
}
