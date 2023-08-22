package com.example.sever;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class Main {

    public static void main(String args[]) {
        JsonObject jsonObject=new JsonObject();
        
        jsonObject.addProperty("name","Tran Xuan Bac");
        jsonObject.addProperty("id",0);
        System.out.println(jsonObject.toString());
        ConnectSever database = null;
        ServerSocket socketServer = null;
        Socket socketClient = null;
        ThreadPoolExecutor executor;

        // Chú ý bạn không thể chọn cổng nhỏ hơn 1023 nếu không là người dùng
        // đặc quyền (privileged users (root)).
        try {
            socketServer = new ServerSocket(9999);
          
            database = new ConnectSever();

        } catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        }

        System.out.println("Server is waiting to accept user...");

        // Nhận được dữ liệu từ người dùng và gửi lại trả lời.

        while (true) {

            try {
                 socketClient = socketServer.accept();
                 System.out.println("Server connected");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            ServerThread serverThread = new ServerThread(socketClient, database);
            executor = new ThreadPoolExecutor(
                    10, // corePoolSize
                    100, // maximumPoolSize
                    10, // thread timeout
                    TimeUnit.SECONDS,
                    new ArrayBlockingQueue<>(10));// queueCapacity
            executor.execute(serverThread);
        }
    }

}
