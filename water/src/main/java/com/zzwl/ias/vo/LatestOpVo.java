package com.zzwl.ias.vo;

import java.util.Date;

/**
 * Created by HuXin on 2017/12/27.
 */
public class LatestOpVo {
    private Date time;
    private int duration;
    private Integer param;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Integer getParam() {
        return param;
    }

    public void setParam(Integer param) {
        this.param = param;
    }
}
