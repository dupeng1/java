package com.example.demo.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

/**
 * 验证器类需要实现ConstraintValidator泛型接口
 * 第一个泛型参数类型Check：注解
 * 第二个泛型参数类型Object：校验字段类型
 * 需要实现initialize、isValid方法
 * isValid方法为校验逻辑，initialize方法初始化工作
 */
public class ParamConstraintValidated implements ConstraintValidator<Check, Object> {
    /**
     * 合法的参数值，从注解获取
     */
    private List<String> paramValues;

    @Override
    public void initialize(Check constraintAnnotation){
        //初始化时获取注解上的值
        paramValues = Arrays.asList(constraintAnnotation.paramValues());
    }

    /**
     *
     * @param o
     * @param constraintValidatorContext
     * @return
     */
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext){
        if(paramValues.contains(o)){
            return true;
        }
        //不在指定参数列表中
        return false;
    }
}
