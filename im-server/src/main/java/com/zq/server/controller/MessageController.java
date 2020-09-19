package com.zq.server.controller;

import com.zq.common.model.MessageDTO;
import com.zq.common.model.UserInfo;
import com.zq.common.utils.Result;
import com.zq.common.utils.TokenUtil;
import com.zq.server.model.Reponse;
import com.zq.server.utils.SessionHolder;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dzeb
 * @version 1.0
 * @Description 消息控制器
 * @createTime 2020/9/19 21:28
 */
@RestController
@RequestMapping
public class MessageController {

    @PostMapping("sendMsg")
    public Result sendMsg(@RequestBody MessageDTO messageDTO) {
        UserInfo loginUser = TokenUtil.getLoginUser();
        messageDTO.setFrom(loginUser.getAccount());
        NioSocketChannel channel = SessionHolder.get(messageDTO.getTo());
        if (channel == null) {
            return Result.failed("用户不在线");
        }
        channel.writeAndFlush(Reponse.newInstance(Reponse.MSG, null, messageDTO));
        return Result.ok();
    }
}
