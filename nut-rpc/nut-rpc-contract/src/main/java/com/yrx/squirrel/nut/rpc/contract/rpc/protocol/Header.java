package com.yrx.squirrel.nut.rpc.contract.rpc.protocol;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.yrx.squirrel.nut.rpc.contract.util.ByteUtils.int2bytes;
import static com.yrx.squirrel.nut.rpc.contract.util.ByteUtils.short2bytes;

/**
 * Created by r.x on 2020/8/23.
 */
@Data
@Builder
@Slf4j
public class Header {
    /**
     * 魔术位：2byte
     */
    private Short magic;
    /**
     * 整体长度：4byte
     */


    private Integer length;
    /**
     * 拓展头长度：2byte
     */
    private Short extHeaderLength;
    /**
     * 版本号：1byte
     */
    private Byte version;
    /**
     * 消息类型：1byte
     */
    private Byte msgType;
    /**
     * 序列化方式：1byte
     */
    private Byte serialization;
    /**
     * 消息id：2byte
     */
    private Short seq;

    public byte[] getBytes() {
        byte[] magic = short2bytes(getMagic());
        byte[] length = int2bytes(getLength());
        byte[] extHeaderLength = short2bytes(getExtHeaderLength());
        byte version = getVersion();
        byte msgType = getMsgType();
        byte serialization = getSerialization();
        byte[] seq = short2bytes(getSeq());

        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        try {
            byteOutputStream.write(magic);
            byteOutputStream.write(length);
            byteOutputStream.write(extHeaderLength);
            byteOutputStream.write(version);
            byteOutputStream.write(msgType);
            byteOutputStream.write(serialization);
            byteOutputStream.write(seq);
        } catch (IOException e) {
            log.error("header getBytes error! header: {}", this.toString(), e);
        }
        return byteOutputStream.toByteArray();

    }

}
