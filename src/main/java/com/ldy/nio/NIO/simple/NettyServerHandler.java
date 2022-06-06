package com.ldy.nio.NIO.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;

/**
 * @author : ldy
 * @version : 1.0
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    /**
     *读取客户端发送的消息
     * 1.ChannelHandlerContext：上下文对象，含有管道pipeline
     * 2.object msg:就是客户端发送的数据
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server ctx = "+ctx);
        System.out.println("查看channel和pipeline的关系！");
        Channel channel = ctx.channel();
        ChannelPipeline pipeline = ctx.pipeline();
        //将msg转成一个bytebuf
        //bytebuf是netty提供的，不是nio的bytebuffer
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("客户端发送消息是：" + byteBuf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址：" + channel.remoteAddress());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //将数据写入到缓存并且刷新
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端，我是服务器",CharsetUtil.UTF_8));

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //处理异常，关闭通道
        ctx.close();
    }
}
