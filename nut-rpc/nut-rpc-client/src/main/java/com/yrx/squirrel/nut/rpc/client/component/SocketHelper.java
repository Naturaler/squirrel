package com.yrx.squirrel.nut.rpc.client.component;

import com.yrx.squirrel.nut.rpc.contract.ParameterDTO;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;
import java.util.StringJoiner;

/**
 * Created by r.x on 2020/7/26.
 */
@Slf4j
public class SocketHelper {

    public static String send(Class targetClass, String targetMethod, List<ParameterDTO<String>> parameters) {
        try {
            // 要连接的服务端IP地址和端口
            String host = "127.0.0.1";
            int port = 55533;
            // 与服务端建立连接
            Socket socket = new Socket(host, port);
            // 建立连接后获得输出流
            OutputStream outputStream = socket.getOutputStream();
            String message = constructMsg(targetClass, targetMethod, parameters);
            log.info("message send to server: {}", message);
            socket.getOutputStream().write(message.getBytes("UTF-8"));
            //通过shutdownOutput高速服务器已经发送完数据，后续只能接受数据
            socket.shutdownOutput();

            InputStream inputStream = socket.getInputStream();
            byte[] bytes = new byte[1024];
            int len;
            StringBuilder sb = new StringBuilder();
            while ((len = inputStream.read(bytes)) != -1) {
                //注意指定编码格式，发送方和接收方一定要统一，建议使用UTF-8
                sb.append(new String(bytes, 0, len, "UTF-8"));
            }
            log.info("get message from server: " + sb);

            inputStream.close();
            outputStream.close();
            socket.close();

            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "server error: " + e.getMessage();
        }
    }

    private static String constructMsg(Class targetClass, String targetMethod, List<ParameterDTO<String>> parameters) {
        StringJoiner joiner = new StringJoiner("|");
        joiner.add(targetClass.getTypeName());
        joiner.add(targetMethod);
        joiner.add(constructParameter(parameters));
        return joiner.toString();
    }

    private static String constructParameter(List<ParameterDTO<String>> parameters) {
        StringJoiner joiner = new StringJoiner(",");
        parameters.forEach(item -> joiner.add(item.toString()));
        return joiner.toString();
    }
}
