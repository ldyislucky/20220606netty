package com.ldy.nio.zerocopy;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author : ldy
 * @version : 1.0
 * 服务器缓存区循环读取该怎么做呢
 */
public class zcServer {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(8888));
        SocketChannel socketChannel = serverSocketChannel.accept();
        System.out.println("客户端上线！");
        ByteBuffer byteBuffer = ByteBuffer.allocate(8192);
        long l = System.currentTimeMillis();
        int he = 0;
        int read = 0;
        while (read!=-1){
            read = socketChannel.read(byteBuffer);
            if (read==-1){
                break;
            }
            byteBuffer.rewind();
            he=he+read;
            System.out.println("读取了"+he+"字节；");
        }
        long l1 = System.currentTimeMillis();
        System.out.println("读取了"+he+"字节；"+"读取了"+(l1-l)+"时间");
    }
}
