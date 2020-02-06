package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 无代码生成和xml配置，spring boot的神奇的不是借助于代码生成来实现的，而是通过条件注解来实现的，这是spring4.x提供的新特性，spring4.x提倡使用Java配置和注解配置组合
 * @EnableAutoConfiguration让springboot根据类路径中的jar包依赖为当前项目进行自动配置，例如添加了spring-boot-starter-web依赖，会自动添加tomcat和spring mvc的依赖，那么
 * springboot会对tomcat和spring mvc进行自动配置，例如，添加了spring-boot-starter-data-jpa依赖，springboot会自动进行jpa相关的配置
 * 1、生成banner
 * http://patorjk.com/software/taag
 */

@RestController
//springboot核心注解，主要目的是开启自动配置
@SpringBootApplication
//@ImportResource({"",""})
public class DemoApplication {
    @Value("${book.author}")
    private String bookAuthor;

    @Value("${book.name}")
    private String bookName;

    @Autowired
    private AuthorSettings authorSettings;

    @RequestMapping("/")
    String index(){
        return "book.author is" + bookAuthor + "book.name is" + bookName;
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
