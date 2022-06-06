package com.ldy.nio.eg1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author : ldy
 * @version : 1.0
 */
public class NIOClient {
  public static void main(String[] args) throws IOException {

    InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1",8888);
    SocketChannel socketChannel = SocketChannel.open(inetSocketAddress);
    socketChannel.configureBlocking(false);
    /*if (!socketChannel.connect(inetSocketAddress)){
      while (!socketChannel.finishConnect()){
        System.out.println("连接需要时间，客户端没有阻塞，可以做其它工作。。");
      }
    }*/
    String str = "日你妈！陌生人......";
    ByteBuffer byteBuffer = ByteBuffer.wrap(str.getBytes());
    socketChannel.write(byteBuffer);
    System.in.read();
  }
}
