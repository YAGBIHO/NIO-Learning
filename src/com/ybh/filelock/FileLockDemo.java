package com.ybh.filelock;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @Author YAGBIHO
 * @Date 2021/9/12 14:19
 **/
public class FileLockDemo {

    //写入数据
    public static void main(String[] args) throws Exception {
        String input = "Java NIO";
        System.out.println("input：" + input);
        ByteBuffer buffer = ByteBuffer.wrap(input.getBytes());
        String filePath = "D:\\CodeHub\\Java\\NIO-Learning\\01.txt";
        Path path = Paths.get(filePath);
        //文件锁只能通过 FileChannel 对象来使用
        FileChannel channel = FileChannel.open(path, StandardOpenOption.WRITE, StandardOpenOption.APPEND);
        channel.position(channel.size() - 1);
        //加锁
        FileLock lock = channel.lock();
        System.out.println("是否为共享锁：" + lock.isShared());
        channel.write(buffer);
        channel.close();
        //读文件
        readFile(filePath);
    }

    //读取数据
    private static void readFile(String filePath) throws Exception {
        FileReader fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String str = bufferedReader.readLine();
        System.out.println("读取出的内容：");
        while (str != null) {
            System.out.println("" + str);
            str = bufferedReader.readLine();
        }
        fileReader.close();
        bufferedReader.close();
    }
}
