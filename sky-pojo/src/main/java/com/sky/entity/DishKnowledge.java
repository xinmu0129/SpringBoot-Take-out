package com.sky.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DishKnowledge {
    private Long id;
    private String dishName;         // 对应 dish_name
    private String ingredients;      // 对应 ingredients
    private String flavorTags;       // 对应 flavor_tags
    private String nutritionalValue; // 对应 nutritional_value
    private String description;      // 对应 description
    private LocalDateTime updateTime;
}