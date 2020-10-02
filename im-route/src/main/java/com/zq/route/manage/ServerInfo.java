package com.zq.route.manage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * @author dzeb
 * @version 1.0
 * @Description
 * @createTime 2020/9/23 23:38
 */
@Data
public class ServerInfo {
    /**
     * ip
     */
    private String ip;
    /**
     * http port
     */
    private int http;
    /**
     * websokcet port
     */
    private int ws;

    public static ServerInfo parse(String path) {
        ServerInfo serverInfo = new ServerInfo();
        String[] attrs = path.split("-")[1].split(":");
        serverInfo.ip = attrs[0];
        serverInfo.http = Integer.valueOf(attrs[1]);
        serverInfo.ws = Integer.valueOf(attrs[2]);
        return serverInfo;
    }

    @JsonIgnore
    public String getWsURL() {
        return "ws://" + ip + ":" + ws + "/websocket";
    }
    @JsonIgnore
    public String getHttpUrl() {
        return "http://" + ip + ":" + http;
    }
}
