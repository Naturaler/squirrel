package com.yrx.squirrel.nut.rpc.client.component;

import com.yrx.squirrel.nut.rpc.contract.ParameterDTO;
import com.yrx.squirrel.nut.rpc.contract.PersonTestProtos;
import com.yrx.squirrel.nut.rpc.contract.rpc.protocol.BlogReqOuter;
import com.yrx.squirrel.nut.rpc.contract.rpc.protocol.Header;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;
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
            // socket.getOutputStream().write(message.getBytes("UTF-8"));
            byte[] payload = createByte();
            socket.getOutputStream().write(header(payload.length, (short) 1));
            socket.getOutputStream().write(payload);

            byte[] payload02 = blogReq();
            socket.getOutputStream().write(header(payload.length, (short) 2));
            socket.getOutputStream().write(payload02);
            //通过shutdownOutput告诉服务器已经发送完数据，后续只能接受数据
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

    private static byte[] blogReq() {
        BlogReqOuter.BlogReq.Builder builder = BlogReqOuter.BlogReq.newBuilder();
        builder.setAuthor("韶华");
        builder.setTitle("光阴如梭");
        builder.setDate(new Date().getTime());
        return builder.build().toByteArray();
    }

    private static byte[] header(int length, short seq) {
        Header header = Header.builder()
                .magic((short) 0)
                .length(length)
                .extHeaderLength((short) 0)
                .version((byte) 1)
                .msgType((byte) 1)
                .serialization((byte) 0)
                .seq(seq)
                .build();
        return header.getBytes();
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

    private static byte[] createByte() throws IOException {
        PersonTestProtos.PersonTest.Builder personBuilder = PersonTestProtos.PersonTest.newBuilder();
        // personTest 赋值
        personBuilder.setName("Jet Chen");
        personBuilder.setEmail("ckk505214992@gmail.com");
        personBuilder.setSex(PersonTestProtos.PersonTest.Sex.MALE);

        // 内部的 PhoneNumber 构造器
        PersonTestProtos.PersonTest.PhoneNumber.Builder phoneNumberBuilder = PersonTestProtos.PersonTest.PhoneNumber.newBuilder();
        // PhoneNumber 赋值
        phoneNumberBuilder.setType(PersonTestProtos.PersonTest.PhoneNumber.PhoneType.MOBILE);
        phoneNumberBuilder.setNumber("17717037257");

        // personTest 设置 PhoneNumber
        personBuilder.addPhone(phoneNumberBuilder);

        // 生成 personTest 对象
        PersonTestProtos.PersonTest personTest = personBuilder.build();

        /** Step2：序列化和反序列化 */
        // 方式一 byte[]：
        // 序列化
//            byte[] bytes = personTest.toByteArray();
        // 反序列化
//            PersonTestProtos.PersonTest personTestResult = PersonTestProtos.PersonTest.parseFrom(bytes);
//            System.out.println(String.format("反序列化得到的信息，姓名：%s，性别：%d，手机号：%s", personTestResult.getName(), personTest.getSexValue(), personTest.getPhone(0).getNumber()));


        // 方式二 ByteString：
        // 序列化
//            ByteString byteString = personTest.toByteString();
//            System.out.println(byteString.toString());
        // 反序列化
//            PersonTestProtos.PersonTest personTestResult = PersonTestProtos.PersonTest.parseFrom(byteString);
//            System.out.println(String.format("反序列化得到的信息，姓名：%s，性别：%d，手机号：%s", personTestResult.getName(), personTest.getSexValue(), personTest.getPhone(0).getNumber()));


        // 方式三 InputStream
        // 粘包,将一个或者多个protobuf 对象字节写入 stream
        // 序列化
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        personTest.writeDelimitedTo(byteArrayOutputStream);
        // byte[] byteArray = byteArrayOutputStream.toByteArray();
        log.info("client byte array: ");
        // for (byte b : byteArray) {
        //     System.out.print(b);
        // }
        // System.out.println();
        byte[] bytes = personTest.toByteArray();
        log.info("client send byte length: {}", bytes.length);
        // for (byte b : bytes) {
        //     System.out.print(b);
        // }
        // System.out.println();
        // PersonTestProtos.PersonTest demo01 = PersonTestProtos.PersonTest.parseFrom(bytes);
        // log.info("client demo: {}", demo01);
        // PersonTestProtos.PersonTest demo = PersonTestProtos.PersonTest.parseFrom(byteArray);
        // log.info("client demo: {}", demo);
        return bytes;
    }
}
