package com.ldy.nio.basic;

import java.nio.ByteBuffer;

/**
 * @author : ldy
 * @version : 1.0
 */
public class BufferPutGet {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(64);
        byteBuffer.putChar('滚');
        //byteBuffer.put("日你妈！陌生人！".getBytes());
        byteBuffer.putInt(88810);
        byteBuffer.flip();
       // System.out.println(new String(byteBuffer.array()));  无法与put get共用
        System.out.println(byteBuffer.getChar());
       // System.out.println( byteBuffer.get());
        System.out.println(byteBuffer.getInt());
    }
}
