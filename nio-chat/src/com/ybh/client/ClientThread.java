package com.ybh.client;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * @Author YAGBIHO
 * @Date 2021/9/14 2:14
 **/
public class ClientThread implements Runnable {

    private Selector selector;

    public ClientThread(Selector selector) {
        this.selector = selector;
    }

    @Override
    public void run() {
        for (;;) {
            //获取Channel数量
            int readChannels = 0;
            try {
                readChannels = selector.select();
                //获取可用的Channel
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                //遍历集合
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    //移除set集合中当前的selectionKey
                    iterator.remove();
                    // 如果是可读状态
                    if (selectionKey.isReadable()) {
                        readOperator(selector, selectionKey);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (readChannels == 0) {
                continue;
            }
        }
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
            System.out.println(message);
        }
    }
}
