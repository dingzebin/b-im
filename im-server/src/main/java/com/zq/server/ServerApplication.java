package com.zq.server;

import com.zq.server.zk.RegistryZookeeper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.net.InetAddress;

/**
 * @author dzeb
 * @version 1.0
 * @Description Server application launcher
 * @createTime 2020/9/11 10:24
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.zq"})
public class ServerApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        String addr = InetAddress.getLocalHost().getHostAddress();
        Thread thread = new Thread(new RegistryZookeeper(addr));
        thread.setName("registry-zookeeper");
        thread.start() ;
    }
}
