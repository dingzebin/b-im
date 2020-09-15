package com.zq.route.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author dzeb
 * @version 1.0
 * @Description 用户
 * @createTime 2020/9/11 17:18
 */
@TableName("user")
@Data
public class User {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @TableField("user_name")
    private String userName;

    @TableField("password")
    private String password;
}
