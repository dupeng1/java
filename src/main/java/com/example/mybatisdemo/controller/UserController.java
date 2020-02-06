package com.example.mybatisdemo.controller;

import com.example.mybatisdemo.entity.User;
import com.example.mybatisdemo.mapper.UserMapper;
import com.example.mybatisdemo.service.UserService;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(path = "/getUser/{id}", method = RequestMethod.GET)
    public String GetUser(@PathVariable int id) {
        /*try {
            InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
            SqlSession session = sqlSessionFactory.openSession();
            String sql = "com.example.mybatisdemo.mapper.UserMapper.Sel";
            User user = session.selectOne(sql, "1");
            System.out.println("------------------"+user.getId()+","+user.getPassWord());
            UserMapper userMapper = session.getMapper(UserMapper.class);
            user = userMapper.Sel(1);
            System.out.println("------------------"+user.getId()+","+user.getPassWord());
            session.close();
        }catch(IOException e){
        }*/
        return userService.Sel(id).toString();
    }
}
