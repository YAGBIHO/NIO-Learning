package com.ybh.client;

/**
 * @Author YAGBIHO
 * @Date 2021/9/14 2:22
 **/
public class Client02 {

    public static void main(String[] args) {
        try {
            new ChatClient().startClient("02");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
