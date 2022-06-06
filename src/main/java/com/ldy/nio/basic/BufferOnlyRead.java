package com.ldy.nio.basic;

import java.nio.ByteBuffer;

/**
 * @author : ldy
 * @version : 1.0
 */
public class BufferOnlyRead {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(64);
        for (int i = 0; i < 64; i++) {
            byteBuffer.put((byte) i);
        }
        byteBuffer.flip();
        ByteBuffer readOnlyBuffer = byteBuffer.asReadOnlyBuffer();
       // byteBuffer.flip();这句话放在这里不行
        while (readOnlyBuffer.hasRemaining()){
            System.out.println(readOnlyBuffer.get());
        }
        //readOnlyBuffer.put((byte) 6);
    }
}
