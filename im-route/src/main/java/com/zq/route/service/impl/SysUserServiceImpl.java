package com.zq.route.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zq.route.entity.User;
import com.zq.route.mapper.SysUserMapper;
import com.zq.route.service.ISysUserService;
import org.springframework.stereotype.Service;

/**
 * @author dzeb
 * @version 1.0
 * @Description 用户
 * @createTime 2020/9/11 17:22
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, User> implements ISysUserService {
}
