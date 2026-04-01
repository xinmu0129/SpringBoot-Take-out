package com.sky.service;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;

//@AiService
public interface AdminChatAssistant {

    // @SystemMessage：定义大模型的人设和行为准则
//    @SystemMessage("你是苍穹外卖管理端的智能助手。请用专业、简洁的中文回答管理员的问题。如果不知道，请直接回答不知道，不要编造。")

    // @MemoryId：这是核心！告诉底层使用哪个 ID 来隔离上下文记忆
    // @UserMessage：用户实际输入的问题
    String chat(@MemoryId Long sessionId, @UserMessage String userMessage);
}