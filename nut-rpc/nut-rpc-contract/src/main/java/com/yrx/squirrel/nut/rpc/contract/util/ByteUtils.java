package com.yrx.squirrel.nut.rpc.contract.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by r.x on 2020/8/24.
 */
public final class ByteUtils {
    private ByteUtils() {
    }

    public static byte[] int2bytes(int intVal) {
        return int2bytes(intVal, ByteOrder.BIG_ENDIAN);
    }

    /**
     * 将int数值转换为占四个字节的byte数组
     *
     * @param intVal    int 要转换的int值
     * @param byteOrder ByteOrder 大小端模式
     * @return byte[]
     */
    public static byte[] int2bytes(int intVal, ByteOrder byteOrder) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.order(byteOrder);
        buffer.asIntBuffer().put(intVal);
        return buffer.array();
    }

    public static Byte[] int2Bytes(int intVal) {
        return int2Bytes(intVal, ByteOrder.BIG_ENDIAN);
    }

    /**
     * 将int数值转换为占四个字节的byte数组
     *
     * @param intVal    int 要转换的int值
     * @param byteOrder ByteOrder 大小端模式
     * @return Byte[]
     */
    public static Byte[] int2Bytes(int intVal, ByteOrder byteOrder) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.order(byteOrder);
        buffer.asIntBuffer().put(intVal);
        byte[] array = buffer.array();
        return bytes2Bytes(array);
    }


    public static int bytes2int(byte[] bytes) {
        return bytes2int(bytes, ByteOrder.BIG_ENDIAN);
    }

    /**
     * 取四个字节的byte数组所代表的int值
     *
     * @param bytes     byte[]
     * @param byteOrder ByteOrder 大小端模式
     * @return int
     */
    public static int bytes2int(byte[] bytes, ByteOrder byteOrder) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        buffer.order(byteOrder);
        return buffer.getInt();
    }

    public static Byte[] short2Bytes(short shortVal) {
        return short2Bytes(shortVal, ByteOrder.BIG_ENDIAN);
    }

    /**
     * 将short数值转换为占两个字节的Byte数组
     *
     * @param shortVal  short 要转换的short值
     * @param byteOrder ByteOrder 大小端模式
     * @return Byte[]
     */
    public static Byte[] short2Bytes(short shortVal, ByteOrder byteOrder) {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.order(byteOrder);
        buffer.asShortBuffer().put(shortVal);
        byte[] array = buffer.array();
        return bytes2Bytes(array);
    }

    public static byte[] short2bytes(short shortVal) {
        return short2bytes(shortVal, ByteOrder.BIG_ENDIAN);
    }

    /**
     * 将short数值转换为占两个字节的byte数组
     *
     * @param shortVal  short 要转换的short值
     * @param byteOrder ByteOrder 大小端模式
     * @return byte[]
     */
    public static byte[] short2bytes(short shortVal, ByteOrder byteOrder) {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.order(byteOrder);
        buffer.asShortBuffer().put(shortVal);
        return buffer.array();
    }

    public static short bytes2short(byte[] bytes) {
        return bytes2short(bytes, ByteOrder.BIG_ENDIAN);
    }

    /**
     * 取两个字节的byte数组所代表的short值
     *
     * @param bytes     byte[]
     * @param byteOrder ByteOrder 大小端模式
     * @return short
     */
    public static short bytes2short(byte[] bytes, ByteOrder byteOrder) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        buffer.order(byteOrder);
        return buffer.getShort();
    }

    /**
     * 将byte[]转为Byte[]
     *
     * @param array byte[]
     * @return Byte[]
     */
    public static Byte[] bytes2Bytes(byte[] array) {
        if (null == array) {
            return null;
        }
        Byte[] bytes = new Byte[array.length];
        for (int i = 0; i < array.length; i++) {
            bytes[i] = array[i];
        }
        return bytes;
    }
}
