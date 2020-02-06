package com.example.demo.rpc.client;


import com.example.demo.rpc.RpcRequest;

import java.lang.reflect.*;

import java.lang.reflect.Method;

/**
 * 客户端代理服务，客户端往服务端发起的调用将通过客户端代理来发起
 */
public class RpcClientProxy implements InvocationHandler {
    //服务端地址
    private String host;
    //服务端端口
    private int port;

    public RpcClientProxy(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * 生成业务接口的代理对象，代理对象做的事情，在invoke方法中
     * @param clazz
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> clazz){
        return (T)Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, RpcClientProxy.this);
    }

    /**
     * 动态代理做的事情，接口的实现不在本地，在网络中的其他进程中，我们通过实现了Rpc客户端的对象来发起远程服务的调用
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object... args) throws Throwable {
        // 调用前
        System.out.println("执行远程方法前，可以做些事情");

        // 调用远程服务，需要封装参数，类似于序列化的过程
        RpcRequest request = new RpcRequest();
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParamTypes(method.getParameterTypes());
        request.setParams(args);
        // 链接服务器调用服务
        RpcClient client = new RpcClient();
        Object rst = client.start(request, host, port);

        // 调用后
        System.out.println("执行远程方法后，也可以做些事情");
        return rst;
    }
}