package com.example.demo.proxy;

public class MyProxyTest {

    public static void main(String[] args) {
        Flyable flyable = MyProxy.newProxyInstance(Flyable.class, new MyInvocationHandler(new Bird()));
        flyable.fly(1000);
    }

}
