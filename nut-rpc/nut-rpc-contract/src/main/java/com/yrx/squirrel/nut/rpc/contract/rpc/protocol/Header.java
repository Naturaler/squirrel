package com.yrx.squirrel.nut.rpc.contract.rpc.protocol;

import lombok.Builder;
import lombok.Data;

import static com.yrx.squirrel.nut.rpc.contract.util.ByteUtils.int2bytes;
import static com.yrx.squirrel.nut.rpc.contract.util.ByteUtils.short2bytes;

/**
 * Created by r.x on 2020/8/23.
 */
@Data
@Builder
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

        byte[] bytes = new byte[13];
        bytes[0] = magic[0];
        bytes[1] = magic[1];
        bytes[2] = length[0];
        bytes[3] = length[1];
        bytes[4] = length[2];
        bytes[5] = length[3];
        bytes[6] = extHeaderLength[0];
        bytes[7] = extHeaderLength[1];
        bytes[8] = version;
        bytes[9] = msgType;
        bytes[10] = serialization;
        bytes[11] = seq[0];
        bytes[12] = seq[1];
        return bytes;

    }

}
