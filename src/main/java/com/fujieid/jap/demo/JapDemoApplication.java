package com.fujieid.jap.demo;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;

import javax.annotation.Resource;

@SpringBootApplication
public class JapDemoApplication implements ApplicationRunner {
    @Resource
    private ServerProperties properties;

    public static void main(String[] args) {
        SpringApplication.run(JapDemoApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("http://localhost:" + properties.getPort());
    }
}
