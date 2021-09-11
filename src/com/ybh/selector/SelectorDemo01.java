package com.ybh.selector;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @Author YAGBIHO
 * @Date 2021/9/11 18:02
 **/
public class SelectorDemo01 {

    public static void main(String[] args) throws Exception {
        //创建Selector选择器
        Selector selector = Selector.open();
        //创建通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //设置通道为非阻塞模式
        serverSocketChannel.configureBlocking(false);
        //绑定连接
        serverSocketChannel.bind(new InetSocketAddress(9999));
        //将通道注册到选择器上,并设置监听事件为：接收
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        //查询已经就绪通道操作
        Set<SelectionKey> selectionKeys = selector.selectedKeys();
        //遍历集合
        Iterator<SelectionKey> iterator = selectionKeys.iterator();
        while (iterator.hasNext()) {
            SelectionKey key = iterator.next();
            //判断key的就绪状态
            if (key.isAcceptable()) {
                // a connection was accepted by a ServerSocketChannel.
            } else if (key.isConnectable()) {
                // a connection was established with a remote server.
            } else if (key.isReadable()) {
                // a channel is ready for reading
            } else if (key.isWritable()) {
                // a channel is ready for writing
            }
            iterator.remove();
        }
    }
}
