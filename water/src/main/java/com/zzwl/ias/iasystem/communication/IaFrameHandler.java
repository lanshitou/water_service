package com.zzwl.ias.iasystem.communication;

import com.zzwl.ias.AppContext;
import com.zzwl.ias.iasystem.device.DeviceManager;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by HuXin on 2017/8/31.
 */
@ChannelHandler.Sharable
public class IaFrameHandler extends ChannelInboundHandlerAdapter {
    private DeviceManager deviceManager;

    public IaFrameHandler() {
        deviceManager = AppContext.deviceManager;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        deviceManager.dispatchFrame(ctx, (IaFrame) msg);
    }

}
