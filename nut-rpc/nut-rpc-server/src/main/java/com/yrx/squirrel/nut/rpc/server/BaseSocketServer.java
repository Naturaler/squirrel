package com.yrx.squirrel.nut.rpc.server;

import com.yrx.squirrel.nut.rpc.client.api.PersonTestProtos;
import com.yrx.squirrel.nut.rpc.contract.IHelloWorld;
import com.yrx.squirrel.nut.rpc.contract.ParameterDTO;
import com.yrx.squirrel.nut.rpc.contract.util.ByteUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
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
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int available = inputStream.available();
        log.info("available: {}",available);
        byte[] header = new byte[13];
        int readHeader = inputStream.read(header);
        short magic = ByteUtils.bytes2short(Arrays.copyOfRange(header, 0, 2));
        int length = ByteUtils.bytes2int(Arrays.copyOfRange(header, 2, 6));
        short extHeaderLength = ByteUtils.bytes2short(Arrays.copyOfRange(header, 6, 8));
        byte version = header[8];
        byte msgType = header[9];
        byte serialization = header[10];
        short seq = ByteUtils.bytes2short(Arrays.copyOfRange(header, 11, 13));
        log.info("magic: {}, length: {}, extHeaderLength: {}, version: {}, msgType: {}, serialization:{}, seq: {}",
                magic, length, extHeaderLength, version, msgType, serialization, seq);
        log.info("read: {}", readHeader);

        final int defaultSize = 1024;
        int actualSize = Math.min(defaultSize, length);
        byte[] payload = new byte[actualSize];
        ByteArrayOutputStream byteArrayStream = new ByteArrayOutputStream();
        int readPayload = -1;
        int readStart = 0;
        int readLength = actualSize;
        int cycle = 0;
        while (readLength > 0 && (readPayload = inputStream.read(payload, readStart, readLength)) > 0) {
            cycle++;
            byteArrayStream.write(payload, 0, readPayload);
            log.info("got it: cycle: {}, size: {}", cycle, actualSize);
            readStart += readLength;
            readLength = Math.min(length - actualSize * cycle, actualSize);
        }
        byte[] byteArray = byteArrayStream.toByteArray();
        log.info("byteArrayStream.toByteArray().length = {}", byteArray.length);
        for (byte b : byteArray) {
            System.out.print(b);
        }
        ByteArrayInputStream byteArrayInput = new ByteArrayInputStream(byteArray);
        PersonTestProtos.PersonTest personTest = PersonTestProtos.PersonTest.parseFrom(byteArrayInput);
        log.info("personTest: {}", personTest);

        // com.yrx.squirrel.nut.rpc.client.api.PersonTestProtos.PersonTest personTestResult = com.yrx.squirrel.nut.rpc.client.api.PersonTestProtos.PersonTest.parseDelimitedFrom(inputStream);
        // System.out.println(String.format("反序列化得到的信息，姓名：%s，性别：%d，手机号：%s", personTestResult.getName(),
        //         personTestResult.getSexValue(), personTestResult.getPhone(0).getNumber()));
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
