package com.zq.common.model;

import lombok.Data;

/**
 * @author dzeb
 * @version 1.0
 * @Description TODO
 * @createTime 2020/9/19 8:55 下午
 */
@Data
public class MessageDTO {
    private String from;
    private String to;
    private String content;
}
