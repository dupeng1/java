package com.example.demo.url;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class URLConnection {
    public static void main(String[] args) throws Exception{
        URL url = new URL("http://www.itmind.net/category/payment-selection/zhishixingqiu-jingxuan/");
        System.out.println("host: " + url.getHost());
        System.out.println("port: " + url.getPort());
        System.out.println("uri_path: " + url.getPath());
        //URLConnection是一个抽象类，代表应用程序和URL资源之间的通信链接。它的实例可用于读取和写入URL引用的资源，该类提供了比Socket类更易于使用、更高级的网络连接对象
        //如果URL协议为http的话，返回的连接为URLConnection的子类HttpURLConnection
        url = new URL("http://www.itmind.net");
        java.net.URLConnection connection = url.openConnection();
        try(InputStream in = connection.getInputStream();){
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = -1;
            while((len = in.read(buffer)) != -1) {
                output.write(buffer, 0, len);
            }
            System.out.println(new String(output.toByteArray()));
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}
