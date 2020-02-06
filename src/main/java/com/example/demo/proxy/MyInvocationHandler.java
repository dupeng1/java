package com.example.demo.proxy;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;

public class MyInvocationHandler implements InvocationHandler {

    private Bird bird;

    public MyInvocationHandler(Bird bird) {
        this.bird = bird;
    }

    @Override   //第一个参数为代理对象，第二个参数为方法对象，后面的参数为方法参数
    public Object invoke(Object proxy, Method method, Object... args) {
        String dateString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
        System.out.println(dateString + "小鸟起飞");
        try {
            Object invoke = method.invoke(bird, args);
            return invoke;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
