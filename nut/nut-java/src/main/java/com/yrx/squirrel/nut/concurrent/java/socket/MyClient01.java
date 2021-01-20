package com.yrx.squirrel.nut.concurrent.java.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by r.x on 2020/9/6.
 */
public class MyClient01 {

    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        final Socket socket = new Socket("127.0.0.1", 22222);

        //回复服务端
        new Thread(){
            public void run() {
                try {
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                    String readline = in.readLine();
                    System.out.println(readline);
                    while(true){
                        out.println(readline);
                        System.out.println(" client send: " + readline);
                        readline = in.readLine();
                        if(readline.equals("bye"))
                            break;
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            };
        }.start();


        new Thread(){
            public void run() {
                //获得客户端发来的数据
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    while(true){
                        System.out.println("Receive from server : " + reader.readLine());
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            };
        }.start();


    }


}
