package com.ldy.nio.GroupChat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

/**
 * @author : ldy
 * @version : 1.0
 */
public class GroupChatServer {
  private Selector selector;
  private ServerSocketChannel serverSocketChannel;
  private static final int post = 8888;

  public GroupChatServer() {
    try {
      selector = Selector.open();
      serverSocketChannel = ServerSocketChannel.open();
      serverSocketChannel.configureBlocking(false);
      serverSocketChannel.socket().bind(new InetSocketAddress(post));
      serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }catch (IOException e){
      e.printStackTrace();
    }
  }
  public void listen(){//监听
    try {
      while (true){
        int count = selector.select(1000);
        if (count!=0){
          Set<SelectionKey> selectionKeys = selector.selectedKeys();
          Iterator<SelectionKey> selectionKeyIterator = selectionKeys.iterator();
          while (selectionKeyIterator.hasNext()){
            SelectionKey selectionKey = selectionKeyIterator.next();
            if (selectionKey.isAcceptable()){
              SocketChannel socketChannel = serverSocketChannel.accept();
              socketChannel.configureBlocking(false);
              socketChannel.register(selector,SelectionKey.OP_READ,ByteBuffer.allocate(1024));
              System.out.println(socketChannel.getRemoteAddress()+"上线");
            }
            if (selectionKey.isReadable()){
              //读取功能
              readData(selectionKey);
            }
            selectionKeyIterator.remove();
          }
        }
      }
    }catch (IOException e){
      e.printStackTrace();
    }
  }
  public void readData(SelectionKey selectionKey){//读取
    SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
    ByteBuffer byteBuffer = (ByteBuffer)selectionKey.attachment();
    try {
        int count = socketChannel.read(byteBuffer);
        if (count>0){
          String str = new String(byteBuffer.array());
          System.out.println(socketChannel.getRemoteAddress()+"客户端："+str);
          //转发功能
          sendInfoOthers(str,socketChannel);
        }
    }catch (Exception e){
      e.printStackTrace();
      try {
        System.out.println(socketChannel.getRemoteAddress()+"下线了。");
        selectionKey.cancel();
        socketChannel.close();
      } catch (IOException ex) {
        ex.printStackTrace();
      }

    }
  }
  public void sendInfoOthers(String str, SocketChannel self){//转发
    System.out.println("服务器转发中");
    try {
      for (SelectionKey selectionKey : selector.keys()){
        Channel channel = selectionKey.channel();
        if (channel instanceof SocketChannel && channel != self){
          SocketChannel dest = (SocketChannel)channel;
          ByteBuffer byteBuffer = ByteBuffer.wrap(str.getBytes());
          dest.write(byteBuffer);
          System.out.println("转发完成！");
          byteBuffer.clear();
        }
      }
    }catch (Exception e){
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    GroupChatServer groupChatServer = new GroupChatServer();
    while (true){
      groupChatServer.listen();
    }
  }
}
