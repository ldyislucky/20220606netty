package com.ldy.nio.zerocopy;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author : ldy
 * @version : 1.0
 */
public class zcClient {
  public static void main(String[] args) throws IOException, InterruptedException {
    SocketChannel socketChannel = SocketChannel.open();
    socketChannel.connect(new InetSocketAddress("127.0.0.1",8888));
    FileChannel fileChannel = new FileInputStream("D:\\D\\document\\javaprograms1" +
            "\\caogao\\c3\\2.mp4").getChannel();
    long l = System.currentTimeMillis();
    long c = fileChannel.size()/8388608;
    c++;
    for (int i = 0; i < c; i++) {
      long lo = fileChannel.transferTo(0, 8388608, socketChannel);
    }
    long l1 = System.currentTimeMillis();
    System.out.println(l1-l);
    while (true){

    }
  }
}
