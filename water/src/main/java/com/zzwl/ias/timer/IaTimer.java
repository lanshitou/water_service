package com.zzwl.ias.timer;

import org.quartz.JobDetail;
import org.quartz.Trigger;

/**
 * Created by HuXin on 2017/9/7.
 */
public class IaTimer {
    private Trigger trigger;
    private JobDetail job;
    private TimeoutHandler handler;

    public IaTimer(JobDetail job, Trigger trigger, TimeoutHandler handler) {
        this.job = job;
        this.trigger = trigger;
        this.handler = handler;
    }

    public Trigger getTrigger() {
        return trigger;
    }

    public JobDetail getJob() {
        return job;
    }

    public TimeoutHandler getHandler() {
        return handler;
    }

}
