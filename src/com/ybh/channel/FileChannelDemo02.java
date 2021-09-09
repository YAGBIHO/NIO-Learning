package com.ybh.channel;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * FileChannel写入数据到Buffer中
 * @Author YAGBIHO
 * @Date 2021/9/9 15:19
 **/
public class FileChannelDemo02 {

    public static void main(String[] args) throws Exception {
        /*
         * 创建FileChannel
         * 参数一：写入的文件路径
         * 参数二：模式，rw：读写模式
         */
        RandomAccessFile accessFile = new RandomAccessFile("D:\\CodeHub\\Java\\NIO-Learning\\001.txt", "rw");
        FileChannel channel = accessFile.getChannel();
        //创建Buffer对象
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        String newData = "Write Java NIO";
        buffer.clear();
        //写入内容
        buffer.put(newData.getBytes());
        buffer.flip();
        //FileChannel完成最终的实现
        while (buffer.hasRemaining()) {
            channel.write(buffer);
        }
        //关闭Channel
        channel.close();
    }
}
