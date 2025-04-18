package org.deslre;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"org.deslre.desk.mapper","org.deslre.user.mapper"})
public class ReactBlogBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactBlogBootApplication.class, args);
    }

}
