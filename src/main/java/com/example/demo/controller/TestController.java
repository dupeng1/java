package com.example.demo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api("接口测试")
@RestController
public class TestController {

    @ApiOperation(value="登陆",notes = "账号密码登陆/微信登陆")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userName",value="账号",required = true, dataType = "String"),
            @ApiImplicitParam(name="password",value="密码",required = true, dataType = "String"),
    })
    @RequestMapping(value="/api/test/login",method = RequestMethod.GET)
    public String login(@RequestParam(value = "userName") String userName,@RequestParam(value = "password") String password){
        return "";
    }
}
