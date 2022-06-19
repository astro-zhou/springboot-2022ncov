package com.zhou.springboot2022ncov;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zhou.springboot2022ncov.dao")
public class Springboot2022ncovApplication {

    public static void main(String[] args) {
        SpringApplication.run(Springboot2022ncovApplication.class, args);
    }

}
