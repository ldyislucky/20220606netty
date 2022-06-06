package com.ldy.nio.eg1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author : ldy
 * @version : 1.0
 */
public class NIOServer {
  public static void main(String[] args) throws Exception {
    //打开服务器接口通道
    ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
    //为服务器接口通道的接口绑定端口号,并监听端口号
    serverSocketChannel.socket().bind(new InetSocketAddress(8888));
    //将服务器接口通道设置为非阻塞
    serverSocketChannel.configureBlocking(false);
    //打开选择器
    Selector selector = Selector.open();
    //将事件为OP_ACCEPT的serverSocketChannel注册到选择器上  register注册
    serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    //等待客户端连接
    while (true){
      //等待1秒，如果没有事件发生，就返回继续别的事件；
      if(selector.select(1000)==0){
        System.out.println("等待了1秒，没有事件发生");
        continue;
      }
      //如果selector.select(1000)>0,就表示已经获取到了关注的事件；
      //然后再通过selector.selectedKeys()获取selectionKeys事件集合
      //再通过selectionKeys集合中selectionKey的状态获取socketChannel通道
      Set<SelectionKey> selectionKeys = selector.selectedKeys();
      Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
      while (keyIterator.hasNext()){
        SelectionKey selectionKey = keyIterator.next();
        //根据selectionKey的状态执行事件
        if (selectionKey.isAcceptable()){//监测注册事件
          SocketChannel socketChannel = serverSocketChannel.accept();
          socketChannel.configureBlocking(false);
          //将关注事件为OP_READ的socketChannel注册到selector，同事给添加一个buffer
          //注意SelectionKey.OP_ACCEPT开头大写
          socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
          System.out.println("注册数量"+selector.keys().size());
        }
        if (selectionKey.isReadable()){//监测读的事件
          //获取注册好的SocketChannel
          SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
          //获取扩展好的ByteBuffer   attachment附属物
          ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();
          socketChannel.read(byteBuffer);
          System.out.println(new String(byteBuffer.array()));
        }
        //从集合中移除迭代器返回的最后一个元素
        keyIterator.remove();
      }
    }

  }
}
