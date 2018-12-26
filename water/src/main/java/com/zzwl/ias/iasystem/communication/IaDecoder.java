package com.zzwl.ias.iasystem.communication;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by HuXin on 2017/8/30.
 */
public class IaDecoder extends ByteToMessageDecoder {

    public IaDecoder() {
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        short len;

        in.markReaderIndex();
        if (in.readableBytes() >= 2) {
            //略过起始0x68
            in.readByte();
            //获取报文总长度
            len = (short) (in.readUnsignedByte() + IaFrame.FRAME_BASIC_LEN);
            in.resetReaderIndex();
            if (in.readableBytes() >= len) {
                IaFrame frame = IaFrame.CreateFrame(in, len);
                if (frame == null) {
                    //报文解析出错了
                    ctx.close();
                } else {
                    out.add(frame);
                }
            }
        }
    }

}
