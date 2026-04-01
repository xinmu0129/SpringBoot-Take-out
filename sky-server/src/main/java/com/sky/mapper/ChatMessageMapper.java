package com.sky.mapper;

import com.sky.annocation.AutoFill;
import com.sky.entity.ChatMessage;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface ChatMessageMapper {

    @Insert("insert into chat_message (session_id, role, content, create_time) values (#{sessionId}, #{role}, #{content}, #{createTime})")
    @Options(useGeneratedKeys = true , keyProperty = "id" , keyColumn = "id")
    @AutoFill(value = OperationType.INSERT)
    void insert(ChatMessage chatMessage);

    @Delete("delete from chat_message where session_id = #{sessionId}")
    void deleteBySessionId(Integer sessionId);
}
