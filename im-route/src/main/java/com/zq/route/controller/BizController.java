package com.zq.route.controller;

import com.zq.common.interceptor.RestTemplateInterceptor;
import com.zq.common.model.MessageDTO;
import com.zq.common.model.UserInfo;
import com.zq.common.utils.RedisUtil;
import com.zq.common.utils.Result;
import com.zq.common.utils.TokenUtil;
import com.zq.route.manage.ServerInfo;
import com.zq.route.manage.ServerManage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author dzeb
 * @version 1.0
 * @Description 业务
 * @createTime 2020/9/14 15:28
 */
@RestController
@RequestMapping
public class BizController {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ServerManage serverManage;

    /**
     * 发送消息
     * @param messageDTO
     * @return
     */
    @PostMapping("sendMsg")
    public Result sendMsg(@RequestBody MessageDTO messageDTO) {
        restTemplate.setInterceptors(Collections.singletonList(new RestTemplateInterceptor()));
        ServerInfo serverInfo = serverManage.getServerByAccount(TokenUtil.getLoginUser().getAccount());
        if (serverInfo == null) {
            return Result.failed("对方已下线");
        }
        ResponseEntity<Result> resultResponseEntity =
                restTemplate.postForEntity(serverInfo.getHttpUrl() + "/sendMsg",
                        messageDTO, Result.class);
        return resultResponseEntity.getBody();
    }

    /**
     * 获取在线用户
     * @return
     */
    @GetMapping("getOnlineUsers")
    public Result getOnlineUsers() {
        String account = TokenUtil.getLoginUser().getAccount();
        Set<String> keys = redisUtil.keys(TokenUtil.TOKEN_PREFIX + "*");
        List<Object> userInfos = redisUtil.mutiGet(keys);
        userInfos = userInfos.stream().filter(u -> !Objects.equals(account, ((UserInfo)u).getAccount())).collect(Collectors.toList());
        return Result.ok(userInfos);
    }
}
