package com.zq.common.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author dzeb
 * @version 1.0
 * @Description 用户
 * @createTime 2020/9/11 17:18
 */
@Data
public class UserInfo implements Serializable {

    private String account;

    private String nickName;
}
