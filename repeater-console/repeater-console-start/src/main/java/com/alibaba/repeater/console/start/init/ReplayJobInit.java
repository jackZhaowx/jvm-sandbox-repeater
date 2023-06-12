package com.alibaba.repeater.console.start.init;

import com.alibaba.repeater.console.dal.dao.ModuleInfoDao;
import com.alibaba.repeater.console.service.jobs.bean.ReplayJobDetail;
import com.alibaba.repeater.console.service.jobs.service.QuartzService;
import org.quartz.JobDataMap;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class ReplayJobInit implements CommandLineRunner {
    @Value("${job.open}")
    private boolean open;
    @Value("${job.replay.open}")
    private boolean replayOpen;
    @Value("${job.replay.cron}")
    private String cron = "*/5 * * * * ?";
    @Resource
    private Scheduler scheduler;
    @Resource
    private QuartzService quartzService;
    @Resource
    private ModuleInfoDao moduleInfoDao;

    public void setOpen(boolean open) {
        this.open = open;
    }

    public void setReplayOpen(boolean replayOpen) {
        this.replayOpen = replayOpen;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    @Override
    public void run(String... strings) throws Exception {
        if (open) {
            scheduler.start();
            if (replayOpen) {
                List<String> appNames = moduleInfoDao.getRecordModule();
                for (String appName : appNames) {
                    JobDataMap jobMap = new JobDataMap();
                    jobMap.put("appName", appName);
                    quartzService.addJob(ReplayJobDetail.class, appName + "Job", "ReplayJob", cron, jobMap, scheduler);
                }
            }
        }
    }
}
