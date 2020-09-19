package com.zq.route.controller;

import com.zq.common.constant.GlobalConstant;
import com.zq.common.model.UserInfo;
import com.zq.common.utils.RedisUtil;
import com.zq.common.utils.Result;
import com.zq.common.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dzeb
 * @version 1.0
 * @Description 登陆注册接口
 * @createTime 2020/9/11 17:24
 */
@RestController
@RequestMapping
public class LoginController {
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 登录
     *
     * @param user
     * @return
     */
    @PostMapping("login")
    public Result login(@RequestBody UserInfo user) {
        if (StringUtils.isEmpty(user.getAccount()) || StringUtils.isEmpty(user.getNickName())) {
            return Result.failed("账号和昵称不能为空");
        }
        if (redisUtil.get(GlobalConstant.TOKEN_PREFIX + user.getAccount()) != null) {
            return Result.failed("账号已存在");
        }
        TokenUtil.setUser(user);
        return Result.ok();
    }
}
