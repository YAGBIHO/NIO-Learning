package com.ybh.buffer;

import org.junit.jupiter.api.Test;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author YAGBIHO
 * @Date 2021/9/10 12:25
 **/
public class BufferDemo01 {

    @Test
    public void buffer01() throws Exception {
        //FileChannel
        RandomAccessFile accessFile = new RandomAccessFile("D:\\CodeHub\\Java\\NIO-Learning\\01.txt", "rw");
        FileChannel channel = accessFile.getChannel();
        //创建Buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //读
        int bytesRead = channel.read(buffer);
        while (bytesRead != -1) {
            //read模式
            buffer.flip();
            while (buffer.hasRemaining()) {
                System.out.println((char)buffer.get());
            }
            buffer.clear();
            bytesRead = channel.read(buffer);
        }
        accessFile.close();
    }

    @Test
    public void buffer02() throws Exception {
        //创建Buffer
        IntBuffer buffer = IntBuffer.allocate(8);
        //向Buffer中放数据
        for (int i = 0; i < buffer.capacity(); i++) {
            int j = 2 * (i + 1);
            buffer.put(j);
        }
        //重置缓冲区
        buffer.flip();
        //获取
        while (buffer.hasRemaining()) {
            int value = buffer.get();
            System.out.println(value + "");
        }
    }
}
