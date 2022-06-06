package com.ldy.nio.NIO.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author : ldy
 * @version : 1.0
 */
public class NettyServer {
    public static void main(String[] args) {
        //bossGroup和workerGroup含有的子线程(nioeventloop)的个数默认 实际CPU核数*2
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //创建程序引导对象
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workerGroup)//设置线程组
                    .channel(NioServerSocketChannel.class)//使用NioServerSocketChannel.class作为服务器通道实现
                    .option(ChannelOption.SO_BACKLOG,128)//设置线程队列得到连接个数
                    .childOption(ChannelOption.SO_KEEPALIVE,true)//设置保持活动连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() {//创建一个通道测试对象
                        //给pipeline设置处理器
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new NettyServerHandler());
                        }
                    }); //给workerGroup的Eventloop的对应管道设置处理器
            System.out.println("服务器准备好了！");
            //绑定端口并且同步，生成一个channelfuture对象，sync是设置成非阻塞、等待异步操作完成
            ChannelFuture channelFuture = serverBootstrap.bind(8888).sync();
            //对关闭的通道进行监听
            channelFuture.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {

        }
    }
}
