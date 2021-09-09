package com.ybh.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * 演示一个非阻塞的accept()方法
 * @Author YAGBIHO
 * @Date 2021/9/9 17:32
 **/
public class ServerSocketChannelDemo {

    public static void main(String[] args) throws Exception {
        //端口号
        int port = 8888;
        //创建Buffer
        ByteBuffer buffer = ByteBuffer.wrap("Hello NIO".getBytes());

        //ServerSocketChannel
        ServerSocketChannel ssc = ServerSocketChannel.open();
        //绑定
        ssc.socket().bind(new InetSocketAddress(port));

        //设置非阻塞模式
        ssc.configureBlocking(false);

        //监听有新链接传入
        while (true) {
            System.out.println("Waiting for connections");
            SocketChannel sc = ssc.accept();
            if (sc == null) { //没有新的链接传入
                System.out.println("没有新的链接传入");
                Thread.sleep(2000);
            }else {
                System.out.println("Incoming connection from：" + sc.socket().getRemoteSocketAddress());
                buffer.rewind(); //指针指向0
                sc.write(buffer);
                sc.close();
            }
        }
    }
}
