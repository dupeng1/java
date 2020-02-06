package com.example.mybatisdemo.mapper;


import com.example.mybatisdemo.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    User Sel(int id);
}
