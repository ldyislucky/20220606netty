package com.ldy.netty;

import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author : ldy
 * @version : 1.0
 */
public class gServerHandler extends SimpleChannelInboundHandler<String> {
    //设置单例管道组
    public static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm");


    //客户端连接执行
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(sdf.format(new Date())+"\n"+channel.remoteAddress()+"用户上线了\n");
        channelGroup.writeAndFlush(sdf.format(new Date())+"\n"+channel.remoteAddress()+"用户上线了\n");
        channelGroup.add(channel);
    }


    @Override
    protected void channelRead0(ChannelHandlerContext chx, String s) throws Exception {
        Channel channelself = chx.channel();
        System.out.println(channelself.remoteAddress()+"\n"+s);
        /*for(Channel channel : channelGroup){
            if (channel == channelself){
                System.out.println("自己说："+s);
            }else {
                System.out.println(channel.remoteAddress()+"说："+s);
            }
        }*/
        channelGroup.forEach(channel -> {
            if (channel==channelself){
                channel.writeAndFlush("我："+s);//这一步错了
            }else {
                channel.writeAndFlush(channel.remoteAddress()+"："+s);
            }
        });
    }
    //客户端断开执行
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(sdf.format(new Date())+"\n"+channel.remoteAddress()+"用户下线了\n");
        channelGroup.writeAndFlush(sdf.format(new Date())+"\n"+channel.remoteAddress()+"用户下线了\n");
       // channelGroup.remove(channel);
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

}
