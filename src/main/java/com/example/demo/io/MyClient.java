package com.example.demo.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class MyClient {
    public static void main(String[] args) throws IOException {
        Socket client = null;
        PrintWriter printWriter = null;
        BufferedReader bufferedReader = null;
        try{
            client = new Socket();
            client.connect(new InetSocketAddress("localhost",8686));
            printWriter = new PrintWriter(client.getOutputStream(), true);
            printWriter.println("hello");
            printWriter.flush();
            bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            System.out.println("来自服务器的信息是："+bufferedReader.readLine());
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            printWriter.close();
            bufferedReader.close();
            client.close();
        }
    }
    /**
     * 试想一下
     * 如果一个客户端请求中，在IO写入到服务端过程中加入sleep，
     * 使每个请求占用服务端线程10s
     * 然后有大量的客户端请求，每个请求都占用那么长时间
     * 那么服务端的并发能力就会大幅度下降
     * 这并不是因为服务端有多少繁重的任务，而仅仅是因为服务线程在等待io（因为accept，read，write都是阻塞式的）
     * 让高速运行的cpu去等待极其低效的网络io是非常不合算的行为
     */
}
