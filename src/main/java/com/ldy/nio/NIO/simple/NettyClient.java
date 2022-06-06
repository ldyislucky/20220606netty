package com.ldy.nio.NIO.simple;

import com.sun.org.apache.bcel.internal.generic.NEW;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author : ldy
 * @version : 1.0
 */
public class NettyClient {
    public static void main(String[] args) {
        //客户端需要一个循环组事件
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            //创建客户端引导对象
            Bootstrap bootstrap = new Bootstrap();

            //设置相关参数
            bootstrap.group(eventLoopGroup)//设置线程组
                    .channel(NioSocketChannel.class)//设置客户端实现通道（反射）
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new NettyClientHandler());
                        }
                    });
            System.out.println("客户端准备好了");
            //本步骤需要分析channelfuture
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8888).sync();
            channelFuture.channel().closeFuture().sync();//没有这一步的话会读取失败
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            eventLoopGroup.shutdownGracefully();
        }

    }


}
