package com.sky.service;

import dev.langchain4j.rag.content.retriever.ContentRetriever;

public interface DishKnowledgeRagService {
    /**
     * 获取 RAG 内容检索器
     */
    ContentRetriever getContentRetriever();
}