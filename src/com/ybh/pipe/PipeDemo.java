package com.ybh.pipe;

import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

/**
 * @Author YAGBIHO
 * @Date 2021/9/11 22:23
 **/
public class PipeDemo {

    public static void main(String[] args) throws Exception {
        //1.获取管道
        Pipe pipe = Pipe.open();
        //2.获取sink通道
        Pipe.SinkChannel sinkChannel = pipe.sink();
        //3.创建缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put("JAVA NIO".getBytes());
        buffer.flip();
        //4.写入数据
        sinkChannel.write(buffer);
        //5.获取source通道
        Pipe.SourceChannel sourceChannel = pipe.source();
        //6.读取数据
        buffer.flip();
        int length = sourceChannel.read(buffer);
        System.out.println(new String(buffer.array(), 0, length));
        //7.关闭
        sinkChannel.close();
        sourceChannel.close();
    }
}
