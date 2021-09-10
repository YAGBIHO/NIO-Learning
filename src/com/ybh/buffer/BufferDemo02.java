package com.ybh.buffer;

import org.junit.jupiter.api.Test;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 缓冲区操作示例
 * @Author YAGBIHO
 * @Date 2021/9/11 2:15
 **/
public class BufferDemo02 {

    //缓冲区分片
    @Test
    public void b01() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put((byte) i);
        }
        //创建子缓冲区
        buffer.position(3); //缓冲区开始位置（包含）
        buffer.limit(7); //缓冲区结束位置（不包含）
        ByteBuffer slice = buffer.slice();
        //改变子缓冲区中的内容
        for (int i = 0; i < slice.capacity(); i++) {
            byte b = slice.get(i);
            b *= 10;
            slice.put(i, b);
        }
        buffer.position(0);
        buffer.limit(buffer.capacity());
        while (buffer.remaining() > 0) {
            System.out.println(buffer.get());
        }
    }

    //只读缓冲区
    @Test
    public void b02() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put((byte) i);
        }
        //创建只读缓冲区
        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();
        for (int i = 0; i < buffer.capacity(); i++) {
            byte b = buffer.get(i);
            b *= 10;
            buffer.put(i, b);
        }
        readOnlyBuffer.position(0);
        readOnlyBuffer.limit(buffer.capacity());
        while (readOnlyBuffer.remaining() > 0) {
            System.out.println(readOnlyBuffer.get());
        }
    }

    //直接缓冲区
    @Test
    public void b03() throws Exception {
        String infile = "D:\\CodeHub\\Java\\NIO-Learning\\01.txt";
        FileInputStream fileInputStream = new FileInputStream(infile);
        FileChannel fileInputChannel = fileInputStream.getChannel();

        String outfile = "D:\\CodeHub\\Java\\NIO-Learning\\02.txt";
        FileOutputStream fileOutputStream = new FileOutputStream(outfile);
        FileChannel fileOutputChannel = fileOutputStream.getChannel();

        //创建直接缓冲区
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
        while (true) {
            buffer.clear();
            int r = fileInputChannel.read(buffer);
            if (r == -1) {
                break;
            }
            buffer.flip();
            fileOutputChannel.write(buffer);
        }
    }

    static private final int start = 0;
    static private final int size = 1024;

    //内存映射文件I/O
    @Test
    public void b04() throws Exception {
        RandomAccessFile raf = new RandomAccessFile("D:\\CodeHub\\Java\\NIO-Learning\\01.txt", "rw");
        FileChannel fc = raf.getChannel();
        MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_WRITE, start, size);
        mbb.put(0, (byte) 97);
        mbb.put(1023, (byte) 122);
        raf.close();
    }
}
