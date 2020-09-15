package com.zq.server.model;

import lombok.Data;

/**
 * @author dzeb
 * @version 1.0
 * @Description TODO
 * @createTime 2020/9/11 13:05
 */
@Data
public class Request {
    /**
     * 请求类型
     */
    private Integer code;
    /**
     * 请求内容
     */
    private String body;
}
