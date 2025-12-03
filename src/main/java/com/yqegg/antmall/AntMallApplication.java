package com.yqegg.antmall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.yqegg.antmall.business.*.mapper")
public class AntMallApplication {

    public static void main(String[] args) {
        SpringApplication.run(AntMallApplication.class, args);
    }

}
