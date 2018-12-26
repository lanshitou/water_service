package com.zzwl.ias.timer;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by HuXin on 2017/9/8.
 */
public class TimeoutJob implements Job {

    public TimeoutJob() {
    }

    public void execute(JobExecutionContext context)
            throws JobExecutionException {
        JobDataMap dataMap = context.getMergedJobDataMap();
        TimeoutHandler handler = (TimeoutHandler) dataMap.getOrDefault("handler", null);
        handler.timeout();
    }
}
