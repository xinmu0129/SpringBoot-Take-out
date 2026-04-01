package com.sky.mapper;

import com.sky.annocation.AutoFill;
import com.sky.entity.ChatSession;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;

@Mapper
public interface ChatSessionMapper {

    @Insert("insert into chat_session (chat_title, admin_id, create_time, update_time) " +
            "values (#{chatTitle}, #{adminId}, #{createTime}, #{updateTime})")
//    @AutoFill(value = OperationType.INSERT)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(ChatSession chatSession);

    @Delete("delete from chat_session where id = #{sessionId}")
    void deleteById(Integer sessionId);

    //TODO 契合AutoFill注解
    @Update("update chat_session set update_time = #{updateTime} where id = #{sessionId}")
    void updateTimeBySeesionId(Long sessionId , LocalDateTime updateTime);
}
