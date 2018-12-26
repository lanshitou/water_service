package com.zzwl.ias.iasystem.device;

/**
 * Created by HuXin on 2018/3/24.
 */
public class ControllerState {
    private Controller controller;
    private Short batteryVoltage;
    private Short solarPanelVoltage;
    private Byte signalStrength;

    public ControllerState(Controller controller) {
        this.controller = controller;
        batteryVoltage = null;
        solarPanelVoltage = null;
        signalStrength = null;
    }

    public Short getBatteryVoltage() {
        return batteryVoltage;
    }

    public void setBatteryVoltage(Short batteryVoltage) {
        this.batteryVoltage = batteryVoltage;
    }

    public Short getSolarPanelVoltage() {
        return solarPanelVoltage;
    }

    public void setSolarPanelVoltage(Short solarPanelVoltage) {
        this.solarPanelVoltage = solarPanelVoltage;
    }

    public Byte getSignalStrength() {
        return signalStrength;
    }

    public void setSignalStrength(Byte signalStrength) {
        this.signalStrength = signalStrength;
    }
}
