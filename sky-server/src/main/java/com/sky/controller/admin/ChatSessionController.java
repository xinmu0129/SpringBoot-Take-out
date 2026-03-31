package com.sky.controller.admin;

import com.sky.dto.SessionDTO;
import com.sky.result.Result;
import com.sky.service.ChatSessionService;
import com.sky.vo.SessionVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/chat")
@Api(tags = "AI智能助手相关接口")
@Slf4j
public class ChatSessionController {

    @Autowired
    private ChatSessionService chatSessionService;

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
}
