package com.yrx.squirrel.nut.rpc.server;

import com.yrx.squirrel.nut.rpc.contract.IHelloWorld;
import com.yrx.squirrel.nut.rpc.contract.ParameterDTO;
import com.yrx.squirrel.nut.rpc.contract.util.ByteUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

/**
 * Created by r.x on 2020/7/26.
 */
@Slf4j
public class BaseSocketServer {

    public static void main(String[] args) throws Exception {
        // 监听指定的端口
        int port = 55533;
        ServerSocket server = new ServerSocket(port);

        // server将一直等待连接的到来
        log.info("server将一直等待连接的到来");
        Socket socket = server.accept();
        // 建立好连接后，从socket中获取输入流，并建立缓冲区进行读取
        InputStream inputStream = socket.getInputStream();
        parseInput(inputStream);
        // byte[] bytes = new byte[1024];
        // int len;
        // StringBuilder sb = new StringBuilder();
        // //只有当客户端关闭它的输出流的时候，服务端才能取得结尾的-1
        // while ((len = inputStream.read(bytes)) != -1) {
        //     // 注意指定编码格式，发送方和接收方一定要统一，建议使用UTF-8
        //     sb.append(new String(bytes, 0, len, "UTF-8"));
        // }
        // log.info("get message from client: " + sb);
        // String result = parseAndExeMsg(sb.toString());
        // log.info("return msg to client: {}", result);

        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("protocol buffer".getBytes("UTF-8"));

        inputStream.close();
        outputStream.close();
        socket.close();
        server.close();
    }

    private static void parseInput(InputStream inputStream) throws IOException {

        byte[] header = new byte[13];
        int read = inputStream.read(header);
        byte[] magic = Arrays.copyOfRange(header, 0, 2);
        short mag = ByteUtils.bytes2short(magic);
        log.info("magic: {}", mag);
        log.info("read: {}", read);
        // inputStream.skip(read);
        // ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(inputStream.);
        com.yrx.squirrel.nut.rpc.client.api.PersonTestProtos.PersonTest personTestResult = com.yrx.squirrel.nut.rpc.client.api.PersonTestProtos.PersonTest.parseDelimitedFrom(inputStream);
        System.out.println(String.format("反序列化得到的信息，姓名：%s，性别：%d，手机号：%s", personTestResult.getName(),
                personTestResult.getSexValue(), personTestResult.getPhone(0).getNumber()));
    }

    private static String parseAndExeMsg(String msg) {
        byte version = 2;
        String[] items = msg.split("\\|");
        String targetClass = items[0];
        String targetMethod = items[1];
        String parameters = items[2];

        String[] parameterDtos = parameters.split("\\$");
        ParameterDTO<String> parameterDTO = new ParameterDTO<>();
        parameterDTO.setName(parameterDtos[0]);
        parameterDTO.setType(parameterDtos[1]);
        parameterDTO.setValue(parameterDtos[2]);

        if (targetClass.equals(IHelloWorld.class.getTypeName())) {
            HelloWorldImpl obj = new HelloWorldImpl();
            return obj.hello(parameterDTO.getValue());
        }
        return "contract error: [" + targetClass + "." + targetMethod + "]";
    }

}