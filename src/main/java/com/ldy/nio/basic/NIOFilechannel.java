package com.ldy.nio.basic;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author : ldy
 * @version : 1.0
 */
public class NIOFilechannel {
    public static void main(String[] args) throws Exception {
        String str = "日你妈！陌生人！";
        FileOutputStream fileOutputStream = new FileOutputStream("D:\\D\\document\\javaprograms1\\caogao" +
                "\\c2增删文件用\\f1.txt");
        FileChannel fileChannel = fileOutputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put(str.getBytes());
        byteBuffer.flip();
        fileChannel.write(byteBuffer);
        fileOutputStream.close();

    }
}
