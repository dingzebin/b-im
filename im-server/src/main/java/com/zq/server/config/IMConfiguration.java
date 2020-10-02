package com.zq.server.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author dzeb
 * @version 1.0
 * @Description IM server configuration
 * @createTime 2020/9/11 10:30
 */
@Component
@Data
public class IMConfiguration {
    /**
     * http port
     */
    @Value("${server.port}")
    private Integer httpPort;

    /**
     * websocket port
     */
    @Value("${im.ws-port}")
    private Integer wsPort;

}
