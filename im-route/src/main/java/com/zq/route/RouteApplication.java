package com.zq.route;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author dzeb
 * @version 1.0
 * @Description TODO
 * @createTime 2020/9/11 16:44
 */
@SpringBootApplication(scanBasePackages = "com.zq")
@MapperScan("com.zq.route.mapper")
public class RouteApplication {
    public static void main(String[] args) {
        SpringApplication.run(RouteApplication.class, args);
    }
}
