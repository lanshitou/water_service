package com.zzwl.ias.iasystem.device;

import com.zzwl.ias.AppContext;
import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.domain.DeviceRecord;
import com.zzwl.ias.iasystem.common.DeviceAddr;
import com.zzwl.ias.iasystem.common.DeviceState;
import com.zzwl.ias.iasystem.common.DeviceType;
import com.zzwl.ias.iasystem.communication.IaCommandType;
import com.zzwl.ias.iasystem.communication.IaFrame;
import com.zzwl.ias.iasystem.communication.analyzer.ControllerStatusInfo;
import com.zzwl.ias.iasystem.communication.analyzer.DeviceStateUpdateInfo;
import com.zzwl.ias.iasystem.communication.analyzer.OperateDeviceReply;
import com.zzwl.ias.vo.DeviceConfigVo;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Description:
 * User: HuXin
 * Date: 2018-05-28
 * Time: 17:00
 */
public class MasterController {
    private static int ONLINE_TTL = 70 * 1000;      //70秒内未收到设备发送的报文，则认为设备离线
    private static int LPC_ONLINE_TTL = 130 * 1000; //低功耗模式130秒没有收到设备发送的报文，则认为设备离线
    private DeviceManager deviceManager;
    private int id;
    private AtomicInteger serialNum;
    //
    private DeviceRecord deviceRecord;
    //以下属性使用controllers进行同步
    private final LinkedList<Controller> controllers;

    //以下属性使用ctxLock进行同步
    private final Object ctxLock;
    private int state;
    private Calendar lastReceiveTime;
    private ChannelHandlerContext ctx;
    private Boolean isDevInfoSynchronized;
    private short syncSn;


    public MasterController(DeviceRecord deviceRecord, DeviceManager deviceManager) {
        this.deviceRecord = deviceRecord;
        this.deviceManager = deviceManager;
        id = DeviceAddr.getSysIdById(deviceRecord.getId());
        serialNum = new AtomicInteger(1);
        controllers = new LinkedList<>();
        addController(deviceRecord);
        ctxLock = new Object();
        lastReceiveTime = Calendar.getInstance();
        lastReceiveTime.setTimeInMillis(0);
        state = DeviceState.DEV_STATE_OFFLINE;
        ctx = null;
        isDevInfoSynchronized = false;
        syncSn = 0;
    }

    public short getCommandSn() {
        return (short) serialNum.getAndIncrement();
    }

    public int getId() {
        return id;
    }

    boolean isLowConsumeBoard() {
        return controllers.getFirst().isLowConsume();
    }


    private ErrorCode addController(DeviceRecord deviceRecord) {
        Controller controller = getController(DeviceAddr.getControllerIdById(deviceRecord.getId()));
        if (controller != null) {
            return ErrorCode.CONTROLLER_EXIST;
        }
        controller = new Controller(deviceRecord, this);
        controllers.add(controller);
        return ErrorCode.OK;
    }

    public synchronized ErrorCode delController(byte ctlerId) {
        for (Controller ctler : controllers) {
            if (ctler.getCtlerId() == ctlerId) {
                controllers.remove(ctler);
                return ErrorCode.OK;
            }
        }
        return ErrorCode.CONTROLLER_NOT_EXIST;
    }

    private Controller getController(byte ctlerId) {
        for (Controller ctler : controllers) {
            if (ctler.getCtlerId() == ctlerId) {
                return ctler;
            }
        }
        return null;
    }

    public synchronized ErrorCode addDevice(DeviceRecord record) {
        if (DeviceAddr.getDevTypeById(record.getId()) == DeviceType.DEV_CONTROLLER) {
            Controller ctler = getController(DeviceAddr.getControllerIdById(record.getId()));
            if (ctler != null) {
                return ErrorCode.CONTROLLER_EXIST;
            }
            return addController(record);
        } else {
            Controller ctler = getController(DeviceAddr.getControllerIdById(record.getId()));
            if (ctler == null) {
                return ErrorCode.CONTROLLER_NOT_EXIST;
            }
            return ctler.addDevice(record);
        }
    }

    public synchronized ErrorCode delDevice(long id) {
        DeviceAddr deviceAddr = new DeviceAddr(id);
        if (deviceAddr.getDevType() == DeviceType.DEV_CONTROLLER) {
            //检查控制器是否存在
            Controller controller = getController(deviceAddr.getCtlerId());
            if (controller == null) {
                return ErrorCode.DEVICE_NOT_EXIST;
            }
            //检查设备下是否还连接着其它设备
            if (deviceAddr.getCtlerId() == 0) {
                //检查首部控制器下是否连接着田间控制器
                if (controllers.size() != 1) {
                    return ErrorCode.DEV_HAS_SUB_DEVICE;
                }
            }
            if (controller.getSubDeviceNumer() != 0) {
                //检查控制器下是否连接着设备
                return ErrorCode.DEV_HAS_SUB_DEVICE;
            }

            //从数据库中删除设备
            ErrorCode errorCode = AppContext.deviceService.delDeviceById(id);
            if (errorCode != ErrorCode.OK) {
                return errorCode;
            }

            //从DeviceManager中删除设备
            delController(deviceAddr.getCtlerId());
        } else {
            //检查设备是否存在
            Controller controller = getController(deviceAddr.getCtlerId());
            if (controller == null) {
                return ErrorCode.DEVICE_NOT_EXIST;
            }
            Device device = controller.getDevice(id);
            if (device == null) {
                return ErrorCode.DEVICE_NOT_EXIST;
            }

            //从数据库中删除
            //从数据库中删除设备
            ErrorCode errorCode = AppContext.deviceService.delDeviceById(id);
            if (errorCode != ErrorCode.OK) {
                return errorCode;
            }
            //从DeviceManager中删除
            controller.delDevice(id);
        }

        return ErrorCode.OK;
    }

    public synchronized ErrorCode updateDevice(DeviceConfigVo deviceConfigVo) {
        DeviceAddr deviceAddr = new DeviceAddr(deviceConfigVo.getId());
        Controller ctler = getController(deviceAddr.getCtlerId());
        if (ctler == null) {
            return ErrorCode.DEVICE_NOT_EXIST;
        }
        return ctler.updateDevice(deviceConfigVo);
    }

    public Device getDevice(long id) {
        synchronized (controllers) {
            Controller ctler = getController(DeviceAddr.getControllerIdById(id));
            if (ctler == null) {
                return null;
            }
            return ctler.getDevice(id);
        }
    }


    public Device getDeviceByCmdSn(short cmdSn) {
        synchronized (controllers) {
            Device dev;
            for (Controller ctler : controllers) {
                dev = ctler.getDeviceByCmdSn(cmdSn);
                if (dev != null) {
                    return dev;
                }
            }
            return null;
        }
    }

    public void checkOnline() {
        synchronized (ctxLock) {
            Calendar now = Calendar.getInstance();
            int keepAlive;
            if (getKeepAlive() == null) {
                if (isLowConsumeBoard()) {
                    keepAlive = LPC_ONLINE_TTL;
                } else {
                    keepAlive = ONLINE_TTL;
                }
            } else {
                keepAlive = getKeepAlive() * 1000;
            }
            if (now.getTimeInMillis() - lastReceiveTime.getTimeInMillis() > keepAlive) {
                //设置设备离线
                setState(DeviceState.DEV_STATE_OFFLINE);
            }
        }
    }

    public void syncDeviceInfo() {
        synchronized (ctxLock) {
            //低功耗单板不进行同步
            if (isLowConsumeBoard()) {
                return;
            }

            if (!isDevInfoSynchronized) {
                syncSn = getCommandSn();
                IaFrame queryDevInfoFrm = new IaFrame(id, syncSn, (short) IaCommandType.QUERY_DEVICE_INFO, null);
                sendFrame(queryDevInfoFrm);
            }
        }
    }

    public void setState(int newState) {
        if (state == DeviceState.DEV_STATE_OK && newState == DeviceState.DEV_STATE_OFFLINE) {
            //设备状态由在线变离线，需要设置所有设备状态为离线
            synchronized (controllers) {
                for (Controller controller : controllers) {
                    controller.setState(DeviceState.DEV_STATE_OFFLINE);
                }
            }

            //关闭当前连接
            if (ctx != null) {
                ctx.close();
                ctx = null;
            }
        } else if (state == DeviceState.DEV_STATE_OFFLINE && newState == DeviceState.DEV_STATE_OK) {
            //设备状态由离线变在线，需要获取设备最新状态
            isDevInfoSynchronized = false;
            syncDeviceInfo();
        }
        state = newState;
    }

    private void updateContext(ChannelHandlerContext ctx) {
        synchronized (ctxLock) {
            if (this.ctx != ctx) {
                if (this.ctx != null) {
                    this.ctx.close();
                }
                this.ctx = ctx;
            }
            setState(DeviceState.DEV_STATE_OK);
            lastReceiveTime = Calendar.getInstance();
        }
    }

    public ErrorCode sendFrame(IaFrame frame) {
        synchronized (ctxLock) {
            ByteBuf buff;
            if (ctx == null) {
                return ErrorCode.CONNECTION_CLOSED;
            }
            buff = frame.generateSendData(ctx);
            ctx.writeAndFlush(buff);
            return ErrorCode.OK;
        }
    }

    public void handleFrame(ChannelHandlerContext ctx, IaFrame frame) {
        updateContext(ctx);
        switch (frame.getType()) {
            case IaCommandType.START_DEVICE:
            case IaCommandType.STOP_DEVICE: {
                handleOperateDeviceReply(frame);
                break;
            }
            case IaCommandType.DEVICE_INFO_UPDATE: {
                handlerDevInfoUpdateFrame(frame);
                break;
            }

            case IaCommandType.QUERY_DEVICE_INFO: {
                handleQueryDevInfoReply(frame);
                break;
            }

            case IaCommandType.KEEP_ALIVE: {
                //链路保活报文，不需要处理
                break;
            }
            case IaCommandType.CONNECTION_SETUP: {
                //需要给首部控制器发送回应报文
                handleConnectFrame(frame);
                break;
            }
            case IaCommandType.CTLER_INFO_UPDATE: {
                handleCtlerInfoUpdateFrame(frame);
                break;
            }
            default: {
                //do nothing
            }
        }
    }

    /**
     * 设备操作命令回应报文处理
     *
     * @param frame
     */
    private void handleOperateDeviceReply(IaFrame frame) {
        Device dev;
        OperateDeviceReply reply = OperateDeviceReply.analyze(id, frame);
        if (reply == null) {
            //报文解析出错
            return;
        }

        if (reply.getResult() != ErrorCode.OK) {
            dev = getDeviceByCmdSn(reply.getSn());
        } else {
            dev = getDevice(reply.getAddr().getId());
        }
        if (dev != null) {
            dev.handleOperationAck(reply);
        }
    }

    /**
     * 设备状态更新报文处理
     *
     * @param frame
     */
    private void handlerDevInfoUpdateFrame(IaFrame frame) {
        List<DeviceStateUpdateInfo> infos = DeviceStateUpdateInfo.analyze(id, frame);
        if (infos != null) {
            for (DeviceStateUpdateInfo info : infos) {
                DeviceAddr addr = info.getAddr();
                if (addr.getDevType() == DeviceType.DEV_CONTROLLER) {
                    synchronized (controllers) {
                        for (Controller controller : controllers) {
                            if (controller.getCtlerId() == addr.getCtlerId()) {
                                if (addr.getCtlerId() == 0) {
                                    controller.setState(DeviceState.DEV_STATE_OK);
                                } else {
                                    controller.setState(info.getState());
                                }
                            }
                        }
                    }
                } else {
                    Device dev = getDevice(info.getAddr().getId());
                    if (dev != null) {
                        dev.updateDevInfo(info.getState(), info.getSensorValues());
                    }
                }
            }
        }

        sendFrame(frame);
    }

    /**
     * 设备状态查询命令回应报文处理
     *
     * @param frame
     */
    private void handleQueryDevInfoReply(IaFrame frame) {
        synchronized (ctxLock) {
            if (frame.getSerialNum() == syncSn) {
                isDevInfoSynchronized = true;
            }
        }
    }

    public void handleConnectFrame(IaFrame frame) {
        //目前，仅简单的回应该报文
        sendFrame(frame);
    }

    public void handleCtlerInfoUpdateFrame(IaFrame frame) {
        ControllerStatusInfo info = ControllerStatusInfo.analyze(id, frame);
        Controller master = controllers.getFirst();
        master.updateBoardStatus(info);
    }


    public DeviceManager getDeviceManager() {
        return deviceManager;
    }

    private Integer getKeepAlive() {
        return getController((byte) 0).getKeepAlive();
    }

    @Override
    public String toString() {
        return "MasterController{" +
                "deviceRecord=" + deviceRecord +
                ", controllers=" + controllers +
                '}';
    }

    public synchronized void collectSensorData(Date date) {
        for (Controller controller : controllers) {
            controller.collectSensorData(date);
        }
    }
}
