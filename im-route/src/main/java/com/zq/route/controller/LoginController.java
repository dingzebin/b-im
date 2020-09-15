package com.zq.route.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;
import com.zq.common.constant.GlobalConstant;
import com.zq.common.model.UserInfo;
import com.zq.common.utils.RedisUtil;
import com.zq.common.utils.Result;
import com.zq.route.entity.User;
import com.zq.route.service.ISysUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

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

    @Autowired
    private ISysUserService sysUserService;

    @PostMapping("register")
    public Result register(@RequestBody User user) {
        if (StringUtils.isEmpty(user.getUserName())) {
            return Result.failed("用户名不能为空");
        }
        if (StringUtils.isEmpty(user.getPassword())) {
            return Result.failed("密码不能为空");
        }
        List<User> users = sysUserService.list(new QueryWrapper<User>().eq("user_name", user.getUserName()));
        if (!users.isEmpty()) {
            return Result.failed("用户名已存在");
        }
        sysUserService.save(user);
        return Result.ok("注册成功");
    }

    /**
     * 登录
     * @param user
     * @return
     */
    @PostMapping("login")
    public Result login(@RequestBody User user) {
        if (StringUtils.isEmpty(user.getUserName()) || StringUtils.isEmpty(user.getPassword())) {
            return Result.failed("用户名或密码不能为空");
        }
        User sysUser = sysUserService.getOne(new QueryWrapper<User>().eq("user_name", user.getUserName()));
        if (sysUser == null) {
            return Result.failed("用户名错误");
        }
        if (!Objects.equals(user.getPassword(), sysUser.getPassword())) {
            return Result.failed("用户名或密码错误");
        }
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(sysUser, userInfo);
        Map<String, Object> result = Maps.newHashMap();
        String token = UUID.randomUUID().toString();
        redisUtil.set(GlobalConstant.TOKEN_PREFIX + token, sysUser, 60);
        result.put("token", token);
        result.put("user", userInfo);
        return Result.ok(result);
    }
}
