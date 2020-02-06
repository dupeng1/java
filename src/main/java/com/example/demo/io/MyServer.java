package com.example.demo.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyServer {
    //创建一个线程池
    private static ExecutorService executorService = Executors.newCachedThreadPool();
    //一旦有新的客户端请求，创建这个线程进行处理
    private static class HandleMsg implements Runnable{
        //创建一个客户端
        Socket client;
        //构造传参绑定
        public HandleMsg(Socket client){
            this.client = client;
        }
        @Override
        public void run() {
            BufferedReader bufferedReader = null;//创建字符缓存输入流
            PrintWriter printWriter = null;//创建字符输出流
            try{
                //获取客户端输入流
                bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                //获取客户端输出流，true是随时刷新
                printWriter = new PrintWriter(client.getOutputStream(),true);
                String inputLine = null;
                long a = System.currentTimeMillis();
                while ((inputLine = bufferedReader.readLine()) != null){
                    printWriter.println(inputLine);
                }
                long b = System.currentTimeMillis();
                System.out.println("此线程花费了："+(b-a)+"秒！");
            }catch (IOException e){
                e.printStackTrace();
            }finally {
                try {
                    bufferedReader.close();
                    printWriter.close();
                    client.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    //服务端的主线程是用来循环监听客户端请求
    public static void main(String[] args) throws IOException{
        //创建一个服务端且端口为8686
        ServerSocket server = new ServerSocket(8686);
        Socket client = null;
        //循环监听
        while(true){
            //服务端监听到一个客户端请求
            client = server.accept();
            System.out.println(client.getRemoteSocketAddress()+"地址的客户端连接成功!");
            //将客户端请求通过线程池放入HandleMsg线程中进行处理
            executorService.submit(new HandleMsg(client));
        }
    }
}
