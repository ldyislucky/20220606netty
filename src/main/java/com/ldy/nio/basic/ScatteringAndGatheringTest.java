package com.ldy.nio.basic;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.function.Function;

/**
 * @author : ldy
 * @version : 1.0
 */
public class ScatteringAndGatheringTest {
    public static void main(String[] args) throws Exception {
        //打开服务器接口通道  一会儿前三步改改方式，不能改，前三步相当于bio中的new ServerSocket(8888);
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //创建网络接口地址
        InetSocketAddress inetSocketAddress = new InetSocketAddress(8888);
        //将网络接口地址绑定到服务器接口通道的接口上并启动
        serverSocketChannel.socket().bind(inetSocketAddress);

        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);

        //等待客户端连接（telnet）,并返回与客户端交互的socketChannel
        SocketChannel socketChannel = serverSocketChannel.accept();
        //假定从客户端接收8个字节
        int messageLenght = 8;
        //循环的读数
        while (true){
            int byteread = 0;
            while (byteread<messageLenght){
                long l = socketChannel.read(byteBuffers);
                //思考一下怎样将读取的数据打印到控制台上
                byteread += l;
                System.out.println("byteread:"+byteread);
                //流打印到窗口  但是目前不太懂
                Arrays.asList(byteBuffers).stream().map(bu->"position:"+bu.position()+","
                        +"limit:"+bu.limit()).forEach(System.out::println);
            }
            //将所有的buffer进行flip   不能放在while外面  会报错的
            Arrays.asList(byteBuffers).forEach(buffer -> buffer.flip());
            //有以下这段程序之后将缓存存区读满了可以继续读，没有这段程序的话缓存读满之后就不再继续读了
            int bytewrite = 0;
            while (bytewrite < messageLenght){
                long l = socketChannel.write(byteBuffers);
                bytewrite += l;
            }

            Arrays.asList(byteBuffers).forEach(buffer -> buffer.clear());
            System.out.println(byteread+","+bytewrite+","+messageLenght);
        }
    }
}
