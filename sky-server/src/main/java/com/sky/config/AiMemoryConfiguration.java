package com.sky.config;

import com.sky.entity.ChatMessage;
import com.sky.mapper.ChatMessageMapper;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;

@Configuration
public class AiMemoryConfiguration {

    @Bean
    public ChatMemoryProvider chatMemoryProvider(ChatMessageMapper chatMessageMapper) {
        return memoryId -> {

            // 1. 创建 memory（限制10条）
            ChatMemory memory = MessageWindowChatMemory.withMaxMessages(10);

            memory.add(SystemMessage.from(
                       "你是苍穹外卖管理端的智能助手。" +
                            "当用户询问订单信息时，请调用系统提供的工具进行查询，而不是自己编造。" +
                            "例如：根据订单ID查询订单详情。"
            ));

            // 2. 查最近10条
            List<ChatMessage> history = chatMessageMapper.getRecentMessages(((Long) memoryId).intValue(), 10);

            if (history != null && !history.isEmpty()) {

                // 3. 变成 正序（旧 → 新）
                Collections.reverse(history);

                // 4. 灌入 memory
                for (ChatMessage msg : history) {
                    if ("user".equals(msg.getRole())) {
                        memory.add(UserMessage.from(msg.getContent()));
                    } else {
                        memory.add(AiMessage.from(msg.getContent()));
                    }
                }
            }
            return memory;
        };
    }
}