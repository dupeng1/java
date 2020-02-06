New IO成功解决了io效率低下的问题，它是怎么解决的呢
IO处理客户端请求的最小单位是线程
而NIO使用了比线程还小一级的单位：通道（Channel）
可以说，NIO只需要一个线程就能完成所有接收、读、写操作
要学习NIO，首先要理解它的三大核心
Selector，选择器
Buffer，缓冲区
Channel通道
一、Buffer
首先要知道什么是Buffer
在NIO中数据交互不再像IO机制那样使用流，而是使用Buffer（缓冲区）
client->Buffer->channel->channel->Buffer->server
分配空间
ByteBuffer bb = ByteBuffer.allocate(1024);
向Buffer中写入数据
从channel->Buffer:channel.read(bb);
从client->Buffer:bb.put(...);
从Buffer中读数据
从Buffer->channel：channel.write(bb);
从Buffer->server: bb.get();
buffer实际上是一个容器，一个连续数组，它通过几个变量来保存这个数据的当前位置状态
1、capacity：容量，缓冲区能容纳元素的数量
2、position：当前位置，是缓冲区中下一次发生读取和写入操作的索引，当前位置通过大多数读写操作向前推进
3、limit：界限，是缓冲区中最后一个有效位置之后下一个位置的索引
二、Selector
选择器是NIO的核心，它是channel的管理者
通过执行select阻塞方法，监听是否有channel准备好
一旦有数据可读，此方法的返回值是SelectionKey的数量
所以服务端通常会死循环执行select方法，直到有channel准备就绪，然后开始工作
每个channel都会和Selector绑定一个事件，然后生成一个SelectionKey的对象
需要注意的是
channel和Selector绑定时，channel必须是非阻塞模式
而FileChannel不能切换到非阻塞模式，因为它不是套接字通道，所以FileChannel不能和Selector绑定事件
在NIO中一共有四种事件
1.SelectionKey.OP_CONNECT：连接事件
2.SelectionKey.OP_ACCEPT：接收事件
3.SelectionKey.OP_READ：读事件
4.SelectionKey.OP_WRITE：写事件
三、channel
共有四种通道：
FileChanel：作用于IO文件流
DatagramChannel：作用于UDP协议
SocketChannel：作用于TCP协议
ServerSocketChannel：作用于TCP协议
打开一个ServerSocketChannel
ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
关闭ServerSocketChannel通道：
serverSocketChannel.close();
循环监听SocketChannel：
while(true){
    SocketChannel socketChannel = serverSocketChannel.accept();
    clientChannel.configureBlocking(false);
}
clientChannel.configureBlocking(false);语句是将此通道设置为非阻塞，也就是异步
自由控制阻塞或非阻塞便是NIO的特性之一
四、SelectionKey
SelectionKey是通道和选择器交互的核心组件
比如在SocketChannel上绑定一个Selector，并注册为连接事件：
SocketChannel clientChannel = SocketChannel.open();
clientChannel.configureBlocking(false);
clientChannel.connect(new InetSocketAddress(port));
clientChannel.register(selector, SelectionKey.OP_CONNECT);
核心register()方法，它返回一个SelectionKey对象
来检测channel事件是哪种事件可以使用以下方法
selectionKey.isAcceptable();
selectionKey.isConnectable();
selectionKey.isReadable();
selectionKey.isWritable();
服务端便是通过这些方法在轮询中执行相对应操作
当然通过Channel与Selector绑定的key也可以反过来拿到它们
Channel  channel  = selectionKey.channel();
Selector selector = selectionKey.selector();
在Channel上注册事件时，我们也可以顺带绑定一个Buffer
clientChannel.register(key.selector(), SelectionKey.OP_READ,ByteBuffer.allocateDirect(1024));
或者绑定一个Object
selectionKey.attach(Object);
Object anthorObj = selectionKey.attachment();

