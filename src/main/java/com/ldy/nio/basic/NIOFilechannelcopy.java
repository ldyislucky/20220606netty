package com.ldy.nio.basic;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * @author : ldy
 * @version : 1.0
 */
public class NIOFilechannelcopy {
  public static void main(String[] args) throws Exception {
    FileInputStream fileInputStream = new FileInputStream("D:\\D\\document\\javaprograms1" +
            "\\caogao\\c3增删源文档\\1.jpg");
    FileOutputStream fileOutputStream = new FileOutputStream("D:\\D\\document\\javaprograms1\\" +
            "caogao\\c2增删文件用\\1.jpg");
    FileChannel fileChannelr = fileInputStream.getChannel();
    FileChannel fileChannelw = fileOutputStream.getChannel();
    //transfer是转移的意思
    fileChannelw.transferFrom(fileChannelr,0,fileChannelr.size());
    fileChannelr.close();
    fileChannelw.close();
    fileInputStream.close();
    fileOutputStream.close();
  }
}
