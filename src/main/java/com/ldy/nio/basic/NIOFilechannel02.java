package com.ldy.nio.basic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author : ldy
 * @version : 1.0
 */
public class NIOFilechannel02 {
    public static void main(String[] args) throws Exception {
        File file = new File("D:\\D\\document\\javaprograms1\\caogao\\c2增删文件用\\f1.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        FileChannel fileChannel = fileInputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());
        fileChannel.read(byteBuffer);
        System.out.println(new String(byteBuffer.array()));
        fileInputStream.close();
    }
}
