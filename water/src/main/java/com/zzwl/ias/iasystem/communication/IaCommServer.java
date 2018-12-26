package com.zzwl.ias.iasystem.communication;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by HuXin on 2017/11/30.
 */

public class IaCommServer extends Thread {
    private int port;
    private IaFrameHandler iaFrameHandler;

    public IaCommServer(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        iaFrameHandler = new IaFrameHandler();

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup);
            b.channel(NioServerSocketChannel.class);
            b.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new IaDecoder(), iaFrameHandler);
                }
            });
            b.option(ChannelOption.SO_BACKLOG, 128);    //socket请求队列的长度
            b.childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture f = b.bind(port).sync();
            f.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }
}
