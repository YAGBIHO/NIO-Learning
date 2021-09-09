package com.ybh.channel;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * 通道之间的数据传输：transferTo()
 * @Author YAGBIHO
 * @Date 2021/9/9 15:58
 **/
public class FileChannelDemo04 {

    public static void main(String[] args) throws Exception {
        //创建两个Channel
        RandomAccessFile accessFile01 = new RandomAccessFile("D:\\CodeHub\\Java\\NIO-Learning\\02.txt", "rw");
        FileChannel fromChannel = accessFile01.getChannel();
        RandomAccessFile accessFile02 = new RandomAccessFile("D:\\CodeHub\\Java\\NIO-Learning\\03.txt", "rw");
        FileChannel toChannel = accessFile02.getChannel();

        //fromChannel传输到toChannel中
        long position = 0;
        long size = fromChannel.size();
        fromChannel.transferTo(position, size, toChannel);

        accessFile01.close();
        accessFile02.close();
        System.out.println("传输结束！");
    }
}
