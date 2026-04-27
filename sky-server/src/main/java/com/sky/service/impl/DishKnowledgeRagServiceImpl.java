package com.sky.service.impl;

import com.sky.entity.DishKnowledge;
import com.sky.mapper.DishKnowledgeMapper;
import com.sky.service.DishKnowledgeRagService;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@Slf4j
public class DishKnowledgeRagServiceImpl implements DishKnowledgeRagService {

    @Autowired
    private DishKnowledgeMapper dishKnowledgeMapper;

    @Autowired
    private EmbeddingModel embeddingModel;

    // 内存向量库，用于存放处理后的菜品知识
    private final EmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();

    /**
     * 项目启动时执行：将数据库数据向量化并加载到内存
     */
    @PostConstruct
    public void init() {
        log.info("开始初始化 AI 菜品知识库 (RAG)...");
        List<DishKnowledge> list = dishKnowledgeMapper.findAll();

        if (list == null || list.isEmpty()) {
            log.warn("数据库 dish_knowledge 表中没有数据，AI 将无法回答口味相关问题。");
            return;
        }

        for (DishKnowledge k : list) {
            // 拼接背景信息，让 AI 检索时有更丰富的上下文
            String content = String.format(
                    "菜品名称：%s。主要食材：%s。口味特征：%s。营养价值：%s。详细描述：%s。",
                    k.getDishName(), k.getIngredients(), k.getFlavorTags(),
                    k.getNutritionalValue(), k.getDescription()
            );

            TextSegment segment = TextSegment.from(content);
            // 调用你测试过的 Embedding 模型进行向量化
            Embedding embedding = embeddingModel.embed(segment).content();
            embeddingStore.add(embedding, segment);
        }
        log.info("AI 菜品知识库初始化完成，共加载 {} 条数据。", list.size());
    }

    @Override
    public ContentRetriever getContentRetriever() {
        return EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(embeddingModel)
                .maxResults(2) // 每次回答参考最相关的 2 条数据
                .minScore(0.6) // 只有匹配度高于 0.6 的知识才会被采用
                .build();
    }
}