package com.sky.config;
//
//import org.springframework.context.annotation.Configuration;
//import dev.langchain4j.memory.chat.ChatMemoryProvider;
//import dev.langchain4j.memory.chat.MessageWindowChatMemory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class LangChainConfiguration {
//
//    @Bean
//    public ChatMemoryProvider chatMemoryProvider() {
//        // 配置记忆提供者：当 AiService 遇到一个新的 MemoryId 时，怎么创建记忆体
//        return memoryId -> {
//            // MessageWindowChatMemory 表示滑动窗口记忆
//            // withMaxMessages(20) 表示每个会话最多记住最近的 20 条消息（包含用户的和AI的）
//            // 这样可以防止聊天时间长了，Token 数量超载导致阿里云扣费过多或报错
//            return MessageWindowChatMemory.withMaxMessages(20);
//        };
//    }
//
//}

import com.sky.ai.tool.OrderTool;
import com.sky.service.AdminChatAssistant;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.service.AiServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LangChainConfiguration {

    @Bean
    public AdminChatAssistant adminChatAssistant(ChatLanguageModel chatLanguageModel,
                                                 ChatMemoryProvider chatMemoryProvider,
                                                 OrderTool orderTool) {

        return AiServices.builder(AdminChatAssistant.class)
                .chatLanguageModel(chatLanguageModel)
                .chatMemoryProvider(chatMemoryProvider)
                .tools(orderTool)
                .build();
    }
}
