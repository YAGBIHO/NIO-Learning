package com.ybh.charset;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Map;
import java.util.Set;

/**
 * @Author YAGBIHO
 * @Date 2021/9/14 0:50
 **/
public class CharsetDemo {

    public static void main(String[] args) throws Exception {
        //1. 获取Charset对象
        Charset charset = Charset.forName("UTF-8");
        //2. 获取编码器对象
        CharsetEncoder charsetEncoder = charset.newEncoder();
        //3. 创建缓冲区
        CharBuffer charBuffer = CharBuffer.allocate(1024);
        charBuffer.put("即将完结");
        charBuffer.flip();
        //4. 编码
        ByteBuffer byteBuffer = charsetEncoder.encode(charBuffer);
        System.out.println("编码之后的结果：");
        for (int i = 0; i < byteBuffer.limit(); i++) {
            System.out.println(byteBuffer.get());
        }
        //5. 获取解码器对象
        byteBuffer.flip();
        CharsetDecoder charsetDecoder = charset.newDecoder();
        //6. 解码
        CharBuffer charBuffer1 = charsetDecoder.decode(byteBuffer);
        System.out.println("解码之后的结果：");
        System.out.println(charBuffer1.toString());

        //获取Charset所支持的所有字符编码
        Map<String,Charset> map = Charset.availableCharsets();
        Set<Map.Entry<String, Charset>> entrySet = map.entrySet();
        for (Map.Entry<String,Charset> entry : entrySet) {
            System.out.println(entry.getKey() + "=" + entry.getValue().toString());
        }
    }
}
