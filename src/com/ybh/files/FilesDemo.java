package com.ybh.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @Author YAGBIHO
 * @Date 2021/9/12 20:13
 **/
public class FilesDemo {

    public static void main(String[] args) {
        //
        Path path = Paths.get("D:\\atguigu");
        try {
            Path directories = Files.createDirectories(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
