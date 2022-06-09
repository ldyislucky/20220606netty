package com.ldy.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author : ldy
 * @version : 1.0
 */
public class gServer {
    private int port;

    public gServer(int port) {
        this.port = port;
    }
    public void run() throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,128)//设置bossGroup的队列个数为128个
                    .childOption(ChannelOption.SO_KEEPALIVE,true)//监听workergroup的连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline channelPipeline = socketChannel.pipeline();
                            channelPipeline.addLast("decoder",new StringDecoder());//暂时还不懂是怎么运行的
                            channelPipeline.addLast("encoder",new StringEncoder());//暂时还不懂是怎么运行的
                            channelPipeline.addLast(new gServerHandler());
                        }
                    });//设置workergroup的事件处理逻辑

            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();//sync()不知道有啥用,至少在本界面没有发现
            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (!channelFuture.isSuccess()){
                        System.out.println("监听失败！");
                    }else {
                        System.out.println("监听成功！");
                    }
                }
            });
            //channelFuture.channel().closeFuture().sync();//1.防止跳转到finally,从而使程序直接结束  2.监听关闭事件，跳转到优雅的关闭
            System.out.println("有closeFuture().sync()的话这句话是打印不出来的！");
        }finally {
            bossGroup.shutdownGracefully();//使线程池优雅退出
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new gServer(8881).run();//有了关闭功能这个还是循环嘛？
    }
}
