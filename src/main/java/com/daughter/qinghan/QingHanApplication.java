package com.daughter.qinghan;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import com.alibaba.nacos.spring.context.annotation.discovery.EnableNacosDiscovery;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
@MapperScan(basePackages = {"com.daughter.qinghan.mapper"})
@EnableNacosDiscovery
@NacosPropertySource(dataId = "qinghan", autoRefreshed = true)
public class QingHanApplication {
    public static void main(String[] args) {
        SpringApplication.run(QingHanApplication.class, args);
        log.info("本地地址:{}","localhost:8081");
    }
}
