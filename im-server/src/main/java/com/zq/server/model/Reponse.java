package com.zq.server.model;

import lombok.Data;

/**
 * @author dzeb
 * @version 1.0
 * @Description TODO
 * @createTime 2020/9/19 22:15
 */
@Data
public class Reponse<T> {
    // 连接成功
    public final static int CONNECTED = 1;
    // 连接失败
    public final static int CONNECT_FAILED = -1;
    // 正常消息
    public final static int MSG = 2;
    // 错误消息
    public final static int ERR = -2;

    private int code;

    private T body;

    private String msg;

    public static <T> Reponse<T> newInstance(int code, String msg, T body) {
        Reponse<T> resp = new Reponse<>();
        resp.code = code;
        resp.msg = msg;
        resp.body = body;
        return resp;
    }
}
