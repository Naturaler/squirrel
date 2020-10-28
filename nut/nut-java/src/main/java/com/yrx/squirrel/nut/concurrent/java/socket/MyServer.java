package com.yrx.squirrel.nut.concurrent.java.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by r.x on 2020/9/6.
 */
public class MyServer {

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(22222);
        while (true) {
            final Socket socket = server.accept();
            System.out.println("accept client socket = " + socket);
            new Thread() {
                public void run() {
                    //获得客户端发来的数据
                    try {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        while (true) {
                            System.out.println("Receive from client : " + reader.readLine());
                        }
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                ;
            }.start();

            new Thread() {
                public void run() {
                    try {
                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                        String readline = in.readLine();
                        System.out.println(readline);
                        while (true) {
                            out.println(readline);
                            System.out.println(" server send: " + readline);
                            readline = in.readLine();
                            if (readline.equals("bye"))
                                break;
                        }
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                ;
            }.start();
        }
    }


}
