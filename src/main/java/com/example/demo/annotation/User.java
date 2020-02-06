package com.example.demo.annotation;

/**
 * 定义一个实体类，对sex字段加校验，其值必须为woman或者man
 */
public class User {
    /**
     * 姓名
     */
    private String name;

    /**
     * 性别man or woman
     */
    @Check(paramValues = {"man","woman"})
    private String sex;
}
