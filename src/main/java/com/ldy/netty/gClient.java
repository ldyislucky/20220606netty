package com.ldy.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

/**
 * @author : ldy
 * @version : 1.0
 */
public class gClient {
    private String host;
    private int post;

    public gClient(String host, int post) {
        this.host = host;
        this.post = post;
    }

    public void  run(){
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast("decoder",new StringDecoder());//暂时还不懂是怎么运行的
                            pipeline.addLast("encoder",new StringEncoder());//暂时还不懂是怎么运行的
                            pipeline.addLast(new gClientHandler());
                        }
                    });
            ChannelFuture channelFuture = bootstrap.connect(host, post);
            Channel channel = channelFuture.channel();
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()){
                String sc = scanner.nextLine();
                channel.writeAndFlush(sc+"\r\n");
                System.out.println("发送成功!"+sc);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            eventLoopGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        gClient gClient = new gClient("127.0.0.1",8881);
        gClient.run();
    }
}
