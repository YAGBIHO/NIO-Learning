package com.ybh.client;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Scanner;

/**
 * 聊天室客户端
 * @Author YAGBIHO
 * @Date 2021/9/14 1:25
 **/
public class ChatClient {

    //启动客户端方法
    public void startClient(String name) throws Exception {
        //1. 连接服务器端
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8000));
        //2. 接收服务端响应的信息
        Selector selector = Selector.open();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
        //创建一个线程
        new Thread(new ClientThread(selector)).start();
        //3. 向服务器端发送消息
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String message = scanner.nextLine();
            if (message.length() > 0) {
                socketChannel.write(Charset.forName("UTF-8").encode(name + " : " + message));
            }
        }
    }
}
