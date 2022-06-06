package com.ldy.nio.GroupChat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * @author : ldy
 * @version : 1.0
 * 客户端也有选择器
 */
public class GroupChatClient1 {
    private String Host = "127.0.0.1";
    private static final int Post = 8888;
    private Selector selector;
    private SocketChannel socketChannel;
    private String username;

    public GroupChatClient1() {
        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open(new InetSocketAddress(Host, Post));
            socketChannel.configureBlocking(false);
            socketChannel.register(selector,SelectionKey.OP_READ);
            username = socketChannel.getLocalAddress().toString();
            System.out.println(username + "  is ok...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendData(String info) {
        info = username + "说：" + info;
        try {
            //ByteBuffer.wrap(byte[] array)   将 byte 数组包装到缓冲区中。
            socketChannel.write(ByteBuffer.wrap(info.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readData() {
        try {
            int ss = selector.select(1000);
            if (ss != 0) {
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> selectionKeyIterator = selectionKeys.iterator();
                while (selectionKeyIterator.hasNext()) {
                    SelectionKey selectionKey = selectionKeyIterator.next();
                    if (selectionKey.isReadable()) {

                        //socketChannel.read(byteBuffer);
                        SocketChannel socketChannel1=(SocketChannel) selectionKey.channel();
                        //ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        socketChannel1.read(byteBuffer);
                        String s = new String(byteBuffer.array());
                        System.out.println(s.trim());
                        byteBuffer.clear();
                    }
                    selectionKeyIterator.remove();//迭代器的这个一定不要忘啊
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        GroupChatClient1 groupChatClient = new GroupChatClient1();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    groupChatClient.readData();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
            groupChatClient.sendData(scanner.next());
        }
    }
}

