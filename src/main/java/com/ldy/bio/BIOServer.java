package com.ldy.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author : ldy
 * @version : 1.0
 */
public class BIOServer {
  public static void main(String[] args) throws IOException {
    //创建服务器线程池
    ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
    //为服务器端口8888提供监听服务；
    ServerSocket serverSocket = new ServerSocket(8888);

    System.out.println("服务器启动了！");
    while (true){
      //使程序处于阻塞状态，指导捕捉到到来自客户端的请求后，返回一个与客户端通信的Socket对象；
      final Socket socket = serverSocket.accept();
      System.out.println("监听到了客户端请求，并连接到客户端！");
      //执行创建的线程任务
      newCachedThreadPool.execute(new Runnable() {
        @Override
        public void run() {
          handler(socket);
        }
      });
    }

  }
  public static void handler(Socket socket){

    try {
      byte[] bytes = new byte[1024];
      InputStream inputStream = socket.getInputStream();
      while (true){
        System.out.println("read....");
        int len = inputStream.read(bytes);//若是未读取到客户端数据，则此程序也为阻塞程序。
        System.out.println("out");
        if (len != -1){
          System.out.println(new String(bytes,0,len));

        }else {
          break;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }finally {
      System.out.println("关闭和客户端的连接");
      try {
        socket.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
