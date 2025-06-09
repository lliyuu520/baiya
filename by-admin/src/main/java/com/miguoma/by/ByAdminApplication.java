package com.miguoma.by;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 启动器
 */
@SpringBootApplication
@EnableScheduling
public class ByAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(ByAdminApplication.class, args);
    }

}
