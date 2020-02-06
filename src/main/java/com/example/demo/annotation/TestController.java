package com.example.demo.annotation;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 注意需要在User对象上加上@Validated注解，这里也可以使用@Valid注解
 */
@RestController("/api/test")
public class TestController {
    @PostMapping
    public Object test(@Validated @RequestBody User user){
        return "hello world";
    }
}
