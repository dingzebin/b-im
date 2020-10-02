package com.zq.route.controller;

import com.zq.common.constant.GlobalConstant;
import com.zq.common.model.UserInfo;
import com.zq.common.utils.RedisUtil;
import com.zq.common.utils.Result;
import com.zq.common.utils.TokenUtil;
import com.zq.route.manage.ServerManage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @author dzeb
 * @version 1.0
 * @Description 获取 server
 * @createTime 2020/9/14 15:17
 */
@RestController
@RequestMapping("/route")
public class RouteController {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ServerManage serverManage;

    /**
     * 登录
     *
     * @param user
     * @return
     */
    @PostMapping("getRoute")
    public Result login(@RequestBody UserInfo user) {
        if (StringUtils.isEmpty(user.getAccount()) || StringUtils.isEmpty(user.getNickName())) {
            return Result.failed("账号和昵称不能为空");
        }
        if (redisUtil.get(GlobalConstant.TOKEN_PREFIX + user.getAccount()) != null) {
            return Result.failed("账号已存在");
        }
        TokenUtil.setUser(user);
        return Result.ok(serverManage.getServer(user.getAccount()).getWsURL());
    }
}
