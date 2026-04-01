package com.sky.controller.admin;

import com.sky.dto.ChatDTO;
import com.sky.dto.SessionDTO;
import com.sky.result.Result;
import com.sky.service.ChatMessageService;
import com.sky.service.ChatSessionService;
import com.sky.vo.ChatVO;
import com.sky.vo.SessionVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/chat")
@Api(tags = "AI智能助手相关接口")
@Slf4j
public class ChatController {

    @Autowired
    private ChatSessionService chatSessionService;

    @Autowired
    private ChatMessageService chatMessageService;

    /**
     * 创建会话
     * @param sessionDTO
     * @return
     */
    @PostMapping("/session")
    @ApiOperation("创建会话")
    public Result<SessionVO> createSession(@RequestBody SessionDTO sessionDTO) {
        log.info("创建会话：{}", sessionDTO);
        SessionVO sessionVO = chatSessionService.createSession(sessionDTO);
        return Result.success(sessionVO);
    }

    /**
     * 删除会话
     * @param sessionId
     * @return
     */
    @DeleteMapping("/session/{sessionId}")
    @ApiOperation("删除会话")
    public Result deleteSession(@PathVariable int sessionId){
        log.info("删除会话：{}", sessionId);
        chatSessionService.deleteSession(sessionId);
        return Result.success();
    }

    /**
     * AI助手聊天
     * @param chatDTO
     * @return
     */
    @PostMapping("/send")
    @ApiOperation("AI助手聊天")
    public Result<ChatVO> send(@RequestBody ChatDTO chatDTO){
        log.info("AI助手聊天：{}", chatDTO);
        if (chatDTO.getSessionId() == null || chatDTO.getMessage() == null) {
            return Result.error("会话ID和消息内容不能为空");
        }
        ChatVO chatVO = chatMessageService.send(chatDTO);
        return Result.success(chatVO);
    }
}
