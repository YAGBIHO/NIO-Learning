package com.ybh.channel;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * FileChannel读取数据到Buffer中
 * @Author YAGBIHO
 * @Date 2021/9/9 14:52
 **/
public class FileChannelDemo01 {

    public static void main(String[] args) throws Exception {
        /*
         * 创建FileChannel
         * 参数一：文件所在路径
         * 参数二：模式，rw：读写模式
         */
        RandomAccessFile accessFile = new RandomAccessFile("D:\\CodeHub\\Java\\NIO-Learning\\01.txt", "rw");
        FileChannel channel = accessFile.getChannel();
        //创建Buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //读取数据到Buffer中
        int bytesRead = channel.read(buffer);
        //等于-1时代表文件已经全部读取完成
        while (bytesRead != -1) {
            System.out.println("读取了" + bytesRead);
            //读写模式反转
            buffer.flip();
            //表示Buffer中是否有剩余的内容
            while (buffer.hasRemaining()) {
                System.out.println((char) buffer.get());
            }
            //清除缓冲区内容
            buffer.clear();
            bytesRead = channel.read(buffer);
        }
        accessFile.close();
        System.out.println("读取结束！");
    }

}
