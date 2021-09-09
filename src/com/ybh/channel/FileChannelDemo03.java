package com.ybh.channel;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * 通道之间的数据传输：transferFrom()
 * @Author YAGBIHO
 * @Date 2021/9/9 15:44
 **/
public class FileChannelDemo03 {

    public static void main(String[] args) throws Exception {
        //创建两个Channel
        RandomAccessFile accessFile01 = new RandomAccessFile("D:\\CodeHub\\Java\\NIO-Learning\\01.txt", "rw");
        FileChannel fromChannel = accessFile01.getChannel();
        RandomAccessFile accessFile02 = new RandomAccessFile("D:\\CodeHub\\Java\\NIO-Learning\\02.txt", "rw");
        FileChannel toChannel = accessFile02.getChannel();

        //fromChannel传输到toChannel中
        long position = 0;
        long size = fromChannel.size();
        /*
        参数一：从哪个Channel获取数据
        参数二：从哪个位置开始写入数据
        参数三：最多传输多少个字节
         */
        toChannel.transferFrom(fromChannel, position, size);

        accessFile01.close();
        accessFile02.close();
        System.out.println("传输结束！");
    }
}
