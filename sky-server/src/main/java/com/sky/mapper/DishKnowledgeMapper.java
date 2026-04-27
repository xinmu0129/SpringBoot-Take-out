package com.sky.mapper;

import com.sky.entity.DishKnowledge;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishKnowledgeMapper {
    @Select("select * from dish_knowledge")
    List<DishKnowledge> findAll();
}
