package com.silita.notice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan("com.silita.notice.dao")
@EnableCaching
public class NoticeWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(NoticeWebApplication.class, args);
    }

}
