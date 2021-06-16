package com.wsq;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wsq.code.mapper")
public class AsianGamesVolunteerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AsianGamesVolunteerApplication.class, args);
    }

}
