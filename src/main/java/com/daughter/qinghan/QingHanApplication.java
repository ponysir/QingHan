package com.daughter.qinghan;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
@MapperScan(basePackages = {"com.daughter.qinghan.mapper"})
public class QingHanApplication {

    public static void main(String[] args) {
        SpringApplication.run(QingHanApplication.class, args);
        log.info("本地地址:{}","localhost:8081");
    }

}
