package com.ybh.server;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * 聊天室服务端
 * @Author YAGBIHO
 * @Date 2021/9/14 1:25
 **/
public class ChatServer {

    //服务器端启动方法
    public void startServer() throws Exception {
        //1. 创建Selector选择器
        Selector selector = Selector.open();
        //2. 创建ServerSocketChannel通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //3. 为Channel通道绑定监听的端口
        serverSocketChannel.bind(new InetSocketAddress(8000));
        //3.1 设置为非阻塞模式
        serverSocketChannel.configureBlocking(false);
        //4. 把Channel通道注册到Selector选择器上
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务器已启动成功");
        //5. 循环，等待有新的链接接入
        for (;;) {
            //获取Channel数量
            int readChannels = selector.select();
            if (readChannels == 0) {
                continue;
            }
            //获取可用的Channel
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            //遍历集合
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                //移除set集合中当前的selectionKey
                iterator.remove();
                //6. 根据就绪状态，调用对应方法实现具体业务操作
                //6.1 如果是Accept状态
                if (selectionKey.isAcceptable()) {
                    acceptOperator(serverSocketChannel, selector);
                }
                //6.2 如果是可读状态
                if (selectionKey.isReadable()) {
                    readOperator(selector, selectionKey);
                }
            }
        }
    }

    //处理接收状态的操作
    private void acceptOperator(ServerSocketChannel serverSocketChannel, Selector selector) throws Exception {
        //1. 接入状态，创建socketChannel
        SocketChannel socketChannel = serverSocketChannel.accept();
        //2. 把socketChannel设置为非阻塞模式
        socketChannel.configureBlocking(false);
        //3. 将channel注册到selector选择器上，并监听可读状态
        socketChannel.register(selector, SelectionKey.OP_READ);
        //4. 客户端回复信息
        socketChannel.write(Charset.forName("UTF-8").encode("已成功接入聊天室"));
    }

    //处理可读状态的操作
    private void readOperator(Selector selector, SelectionKey selectionKey) throws Exception {
        //1. 从selectionKey获取到已经就绪的通道
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        //2. 创建Buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        //3. 读取客户端发过来的消息
        int read = socketChannel.read(byteBuffer);
        String message = "";
        if (read > 0) {
            //切换成读的模式
            byteBuffer.flip();
            //读取模式
            message += Charset.forName("UTF-8").decode(byteBuffer);
        }
        //4. 将channel再次注册到选择器，监听可读状态
        socketChannel.register(selector, SelectionKey.OP_READ);
        //5. 把客户端发送来的消息，广播到其他客户端
        if (message.length() > 0) {
            //广播给其他的客户端
            castOtherClient(message, selector, socketChannel);
        }
    }

    //广播到其他客户端
    private void castOtherClient(String message, Selector selector, SocketChannel socketChannel) throws Exception {
        //1. 获取到所有已经接入的客户端
        Set<SelectionKey> selectionKeySet = selector.keys();
        //2. 循环向所有的channel广播消息
        for (SelectionKey selectionKey : selectionKeySet) {
            //获取里面的每个channel
            Channel channel = selectionKey.channel();
            //不需要给自己的channel发送
            if (channel instanceof SocketChannel && channel != socketChannel) {
                ((SocketChannel) channel).write(Charset.forName("UTF-8").encode(message));
            }
        }
    }

    public static void main(String[] args) {
        try {
            new ChatServer().startServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
