package com.example.demo.rpc.server;

import org.junit.Test;

public class ServerTest {
    @Test
    public void startServer() {
        //给出一个端口号，参数中带有一个包，功能是扫描某个包下的服务
        RpcServer server = new RpcServer();
        server.start(9998, "com.example.demo.rpc");
    }

    public static void main(String[] args) {
    }

}
