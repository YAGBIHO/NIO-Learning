package com.ybh.channel;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @Author YAGBIHO
 * @Date 2021/9/9 19:38
 **/
public class SocketChannelDemo {

    public static void main(String[] args) throws Exception {
        //创建SocketChannel
        //方法一
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("www.baidu.com", 80));
        //方法二
        //SocketChannel socketChanne2 = SocketChannel.open();
        //socketChanne2.connect(new InetSocketAddress("www.baidu.com", 80));

        //设置阻塞和非阻塞
        socketChannel.configureBlocking(false);

        //读操作
        ByteBuffer byteBuffer = ByteBuffer.allocate(16);
        socketChannel.read(byteBuffer);
        socketChannel.close();
        System.out.println("read over");
    }
}
