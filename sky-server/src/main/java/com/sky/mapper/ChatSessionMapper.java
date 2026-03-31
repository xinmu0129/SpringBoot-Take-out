package com.sky.mapper;

import com.sky.entity.ChatSession;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface ChatSessionMapper {

    @Insert("insert into chat_session (chat_title, admin_id, create_time, update_time) " +
            "values (#{chatTitle}, #{adminId}, #{createTime}, #{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(ChatSession chatSession);
}
