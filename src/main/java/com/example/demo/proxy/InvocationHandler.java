package com.example.demo.proxy;

import java.lang.reflect.Method;

//执行方法
public interface InvocationHandler {
    //第一个参数，代理对象，第二个参数，所执行的方法反射对象，第三个参数及以后，传入的参数
    Object invoke(Object proxy, Method method, Object... args);
}
