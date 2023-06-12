package com.alibaba.repeater.console.service.jobs.service;

import org.quartz.Scheduler;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Map;

public interface QuartzService {
    void addJob(Class<? extends QuartzJobBean> jobClass, String jobName, String jobGroupName, int jobTime,
                int jobTimes, Map jobData, Scheduler scheduler);

    void addJob(Class<? extends QuartzJobBean> jobClass, String jobName, String jobGroupName, String jobTime, Map jobData, Scheduler scheduler);
}
