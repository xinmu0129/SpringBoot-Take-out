package com.sky.mapper;

import com.sky.annocation.AutoFill;
import com.sky.entity.ChatMessage;
import com.sky.enumeration.OperationType;
import com.sky.vo.ChatHistoryVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ChatMessageMapper {

    @Insert("insert into chat_message (session_id, role, content, create_time , timestamps) values (#{sessionId}, #{role}, #{content}, #{createTime} , #{timestamp})")
    @Options(useGeneratedKeys = true , keyProperty = "id" , keyColumn = "id")
//    @AutoFill(value = OperationType.INSERT)
    void insert(ChatMessage chatMessage);

    @Delete("delete from chat_message where session_id = #{sessionId}")
    void deleteBySessionId(Integer sessionId);

    @Select("select * from chat_message where session_id = #{sessionId} order by create_time ASC")
    List<ChatMessage> getBySessionId(Integer sessionId);
}
