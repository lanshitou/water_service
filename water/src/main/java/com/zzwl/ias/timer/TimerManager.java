package com.zzwl.ias.timer;

import com.zzwl.ias.common.ErrorCode;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Component;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.DateBuilder.futureDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created by HuXin on 2017/9/7.
 */
@Component
public class TimerManager {
    private Scheduler scheduler;

    public void Init() {
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public IaTimer createTimer(TimeoutHandler handler, int timeout, boolean repeat) {
        JobDetail job = newJob(TimeoutJob.class).build();
        job.getJobDataMap().put("handler", handler);

        Trigger trigger;
        if (repeat) {
            trigger = newTrigger()
                    .startAt(futureDate(timeout, DateBuilder.IntervalUnit.SECOND))
                    .withSchedule(simpleSchedule()
                            .withIntervalInSeconds(timeout)
                            .repeatForever())
                    .build();
        } else {
            trigger = newTrigger()
                    .startAt(futureDate(timeout, DateBuilder.IntervalUnit.SECOND))
                    .build();
        }

        IaTimer timer = new IaTimer(job, trigger, handler);

        try {
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        return timer;
    }

    public IaTimer createCronTimer(TimeoutHandler handler, String cron) {
        JobDetail job = newJob(TimeoutJob.class).build();
        job.getJobDataMap().put("handler", handler);

        Trigger trigger = newTrigger()
                .withSchedule(cronSchedule(cron))
                .build();

        IaTimer timer = new IaTimer(job, trigger, handler);

        try {
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        return timer;
    }


    public ErrorCode deleteTimer(IaTimer timer) {
        try {
            scheduler.pauseTrigger(timer.getTrigger().getKey());
            scheduler.unscheduleJob(timer.getTrigger().getKey());
            scheduler.deleteJob(timer.getJob().getKey());
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return ErrorCode.OK;
    }
}
