package com.zzwl.ias.iasystem.communication;

import com.zzwl.ias.common.Crc;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by HuXin on 2017/8/30.
 */
public class IaFrame {
    /* 报文格式为:协议号0x68 + 1字节用户数据长度 + 起始标识0x68 + 4字节系统ID + 2字节命令序列号 + 1字节命令类型 + 用户数据 + 2字节CRC + 结束标识0x16
     * 除用户数据部分，报文其余部分的长度为13，需要进行CRC计算部分的长度为9 */
    public static int FRAME_BASIC_LEN = 13;
    public static int CRC_BASIC_LEN = 9;
    private int sysId;
    private short serialNum;
    private short type;
    private byte[] data;

    private IaFrame() {
    }

    public IaFrame(int sysId, short serialNum, short type, byte[] data) {
        this.sysId = sysId;
        this.serialNum = serialNum;
        this.type = type;
        this.data = data;
    }

    public ByteBuf generateSendData(ChannelHandlerContext ctx) {
        ByteBuf buff;
        short crc;
        int crcStartIndex;

        if (data != null) {
            buff = ctx.alloc().buffer(data.length + FRAME_BASIC_LEN);
        } else {
            buff = ctx.alloc().buffer(FRAME_BASIC_LEN);
        }

        //协议号0x68
        buff.writeByte(0x68);
        //用户数据长度
        crcStartIndex = buff.writerIndex();
        if (data != null) {
            buff.writeByte(data.length);
        } else {
            buff.writeByte(0);
        }
        //起始标识0x68
        buff.writeByte(0x68);
        //系统ID
        buff.writeInt(sysId);
        //命令序列号
        buff.writeShort(serialNum);
        //命令类型
        buff.writeByte((byte) type);
        //用户数据和CRC
        if (data != null) {
            buff.writeBytes(data);
            crc = Crc.calcCrc16ForByteBuf(buff, crcStartIndex, data.length + CRC_BASIC_LEN);
        } else {
            crc = Crc.calcCrc16ForByteBuf(buff, crcStartIndex, CRC_BASIC_LEN);
        }
        buff.writeShort(crc);
        //结束0x16
        buff.writeByte(0x16);

        return buff;
    }


    /**
     * 根据接收到的数据构造IaFrame
     *
     * @param in  接收到的数据
     * @param len 数据的长度
     * @return 成功返回构造的IaFrame，失败返回null
     */
    static public IaFrame CreateFrame(ByteBuf in, int len) {
        int readIndex;
        short crc;
        boolean valid;
        byte[] userData;
        int userDataLen;
        IaFrame frame = new IaFrame();

        valid = true;
        do {
            //计算CRC
            readIndex = in.readerIndex();
            //CRC计算从用户数据长度字节开始到用户数据结尾，长度为总长度 - 4（1字节协议号0x68 + 2字节CRC + 1字节结束标识0x16）
            crc = Crc.calcCrc16ForByteBuf(in, readIndex + 1, len - 4);

            //起始0x68
            if (in.readByte() != 0x68) {
                valid = false;
                break;
            }

            //用户数据长度
            userDataLen = in.readUnsignedByte();

            //第二个0x68
            if (in.readByte() != 0x68) {
                valid = false;
                break;
            }

            //子系统ID
            frame.setSysId(in.readInt());

            //命令序列号
            frame.setSerialNum(in.readShort());

            //命令类型
            frame.setType(in.readUnsignedByte());

            //读取用户数据
            if (userDataLen != 0) {
                userData = new byte[userDataLen];
                in.readBytes(userData);
            } else {
                userData = null;
            }
            frame.setData(userData);

            //读取及校验CRC
            if (crc != in.readShort()) {
                valid = false;
                break;
            }

            //结尾0x16
            if (in.readByte() != 0x16) {
                valid = false;
                break;
            }

        } while (false);

        if (valid) {
            return frame;
        } else {
            return null;
        }
    }

    public int getSysId() {
        return sysId;
    }

    private void setSysId(int sysId) {
        this.sysId = sysId;
    }

    public short getSerialNum() {
        return serialNum;
    }

    private void setSerialNum(short serialNum) {
        this.serialNum = serialNum;
    }

    public short getType() {
        return type;
    }

    private void setType(short type) {
        this.type = type;
    }

    public byte[] getData() {
        return data;
    }

    private void setData(byte[] data) {
        this.data = data;
    }
}
