package com.ybh.path;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @Author YAGBIHO
 * @Date 2021/9/12 19:15
 **/
public class PathDemo01 {

    public static void main(String[] args) {
        //创建Path实例
        Path path = Paths.get("D:\\CodeHub\\Java\\NIO-Learning\\01.txt");
        String originalPath = "D:\\CodeHub\\Java\\..\\NIO-Learning";
        Path path1 = Paths.get(originalPath);
        System.out.println("path1 = " + path1);
        Path path2 = path1.normalize();
        System.out.println("path2 = " + path2);
    }
}
