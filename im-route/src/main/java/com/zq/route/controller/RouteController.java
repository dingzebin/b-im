package com.zq.route.controller;

import com.zq.common.utils.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dzeb
 * @version 1.0
 * @Description 获取 server
 * @createTime 2020/9/14 15:17
 */
@RestController
@RequestMapping("/route")
public class RouteController {


    @GetMapping
    public Result server() {
        return Result.ok("ws://localhost:2408/websocket");
    }
}
