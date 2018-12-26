package com.zzwl.ias;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.zzwl.ias.mapper")
public class IasApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(IasApplication.class);
        application.run(args);
    }
}
