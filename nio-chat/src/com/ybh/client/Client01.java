package com.ybh.client;

/**
 * @Author YAGBIHO
 * @Date 2021/9/14 2:21
 **/
public class Client01 {

    public static void main(String[] args) {
        try {
            new ChatClient().startClient("01");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
