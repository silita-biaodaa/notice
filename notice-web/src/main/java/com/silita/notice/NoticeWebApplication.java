package com.silita.notice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.silita.notice.dao")
//@EnableCaching
public class NoticeWebApplication {//implements CommandLineRunner{

    public static void main(String[] args) {
        SpringApplication.run(NoticeWebApplication.class, args);
    }

//    @Autowired
//    private TbNtMianHunanService tbNtMianHunanService;

//    @Override
//    public void run(String... strings) throws Exception {
//        tbNtMianHunanService.saveNoticeContentForMysql();
//    }
}
