package com.sky.mapper;

import com.sky.annocation.AutoFill;
import com.sky.entity.ChatSession;
import com.sky.enumeration.OperationType;
import com.sky.vo.ChatListVO;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

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

    @Select("SELECT " +
            "cs.id AS sessionId, " +
            "cs.chat_title AS sessionTitle, " +
            "(SELECT LEFT(cm.content, 30) FROM chat_message cm " +
            " WHERE cm.session_id = cs.id " +
            " ORDER BY cm.create_time DESC LIMIT 1) AS lastMessage, " +
            "cs.create_time AS createTime, " +
            "cs.update_time AS updateTime " +
            "FROM chat_session cs " +
            "WHERE cs.admin_id = #{adminId} " +
            "AND cs.is_deleted = 0 " +
            "ORDER BY cs.update_time DESC")
    List<ChatListVO> getByAdminId(Long adminId);
}
