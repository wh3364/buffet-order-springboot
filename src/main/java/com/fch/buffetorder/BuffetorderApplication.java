package com.fch.buffetorder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.fch.buffetorder.mapper")
public class BuffetorderApplication {

    public static void main(String[] args) {
        SpringApplication.run(BuffetorderApplication.class, args);
    }

}
