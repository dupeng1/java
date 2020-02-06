package com.example.demo.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class MyNioServer {
    //创建一个选择器
    private Selector selector;
    private final static int port = 8686;
    private final static int BUF_SIZE = 10240;
    private void initServer() throws IOException{
        //创建通道管理器对象selector
        this.selector = Selector.open();
        //创建一个通道对象channel
        ServerSocketChannel channel = ServerSocketChannel.open();
        //将通道设置为非阻塞
        channel.configureBlocking(false);
        //将通道绑定在8686端口
        channel.socket().bind(new InetSocketAddress(port));

        //将上述通道管理器和通道绑定，并为该通道注册OP_ACCEPT事件
        //注册事件后，当该事件到达时，
        SelectionKey selectionKey = channel.register(selector, SelectionKey.OP_ACCEPT);

    }
}
