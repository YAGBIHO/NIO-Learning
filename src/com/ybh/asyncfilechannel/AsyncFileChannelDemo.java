package com.ybh.asyncfilechannel;

import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Future;

/**
 * @Author YAGBIHO
 * @Date 2021/9/13 11:57
 **/
public class AsyncFileChannelDemo {

    @Test
    public void readAsyncFileChannelFuture() throws Exception {
        //1. 创建AsyncFileChannel
        Path path = Paths.get("D:\\CodeHub\\Java\\NIO-Learning\\01.txt");
        AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);
        //2. 创建Buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //3. 调用Channel的read方法得到Future
        Future<Integer> future = fileChannel.read(buffer, 0);
        //4. 判断Future是否已经完成，isDone()
        while (!future.isDone());
        //5. 读取数据到Buffer里
        buffer.flip();
        byte[] data = new byte[buffer.limit()];
        buffer.get(data);
        System.out.println(new String(data));
        buffer.clear();
    }

    @Test
    public void readAsyncFileChannelCompletion() throws Exception {
        //1. 创建AsyncFileChannel
        Path path = Paths.get("D:\\CodeHub\\Java\\NIO-Learning\\01.txt");
        AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);
        //2. 创建Buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //3. CompletionHandler作为参数读取
        fileChannel.read(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                System.out.println("result：" + result);
                //4. 读取数据到Buffer里
                attachment.flip();
                byte[] data = new byte[attachment.limit()];
                attachment.get(data);
                System.out.println(new String(data));
                attachment.clear();
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {

            }
        });
    }

    @Test
    public void writeAsyncFileChannelFuture() throws Exception {
        //1. 创建AsyncFileChannel
        Path path = Paths.get("D:\\CodeHub\\Java\\NIO-Learning\\01.txt");
        AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE);
        //2. 创建Buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //3. write方法
        buffer.put("JAVA NIO".getBytes());
        buffer.flip();
        Future<Integer> future = fileChannel.write(buffer, 0);
        while (!future.isDone());
        buffer.clear();
        System.out.println("write over");
    }

    @Test
    public void writeAsyncFileChannelCompletion() throws Exception {
        //1. 创建AsyncFileChannel
        Path path = Paths.get("D:\\CodeHub\\Java\\NIO-Learning\\01.txt");
        AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE);
        //2. 创建Buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //3. write方法
        buffer.put("JAVA NIO".getBytes());
        buffer.flip();
        fileChannel.write(buffer, 0, 0, new CompletionHandler<Integer, Integer>() {
            @Override
            public void completed(Integer result, Integer attachment) {
                System.out.println("bytes written" + result);
            }

            @Override
            public void failed(Throwable exc, Integer attachment) {

            }
        });
        buffer.clear();
        System.out.println("write over");
    }
}
