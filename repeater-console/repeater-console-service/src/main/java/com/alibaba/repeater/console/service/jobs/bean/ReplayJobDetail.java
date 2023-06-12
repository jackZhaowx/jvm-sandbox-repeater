package com.alibaba.repeater.console.service.jobs.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.repeater.console.common.params.ReplayParams;
import com.alibaba.repeater.console.dal.dao.ModuleInfoDao;
import com.alibaba.repeater.console.dal.dao.RecordDao;
import com.alibaba.repeater.console.dal.model.ModuleInfo;
import com.alibaba.repeater.console.dal.model.Record;
import com.alibaba.repeater.console.service.ReplayService;
import com.alibaba.repeater.console.service.jobs.util.AppContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;

@Slf4j
@DisallowConcurrentExecution // 保证多个推送在同一时间时，不并发执行
@PersistJobDataAfterExecution
public class ReplayJobDetail extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        RecordDao recordDao = AppContextUtil.getBean(RecordDao.class);
        ModuleInfoDao moduleInfoDao = AppContextUtil.getBean(ModuleInfoDao.class);
        ReplayService replayService = AppContextUtil.getBean(ReplayService.class);
        JobDetail jobDetail = context.getJobDetail();
        Trigger trigger = context.getTrigger();
        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        String appName = (String) jobDataMap.get("appName");
        log.info("正在使用【{}】trigger组的【{}】trigger,执行【{}】任务组的【{}】任务，带入参数为【{}】，执行时间为【{}】", trigger.getKey().getGroup(), trigger.getKey().getName(), jobDetail.getKey().getGroup(), jobDetail.getKey().getName(), JSON.toJSON(jobDetail.getJobDataMap()), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(context.getFireTime()));
        List<ModuleInfo> moduleInfos = moduleInfoDao.queryRepeat(appName);
        if (moduleInfos != null && moduleInfos.size() > 0) {
            List<Record> recordList = recordDao.selectNotRepaly(appName);
            Random random = new Random();
            for (Record record : recordList) {
                //遍历执行回放
                String traceId = record.getTraceId();
                //查询本条记录的回放机器（多个机器随机选择一个）
                ModuleInfo moduleInfo = moduleInfos.get(random.nextInt(moduleInfos.size()));
                ReplayParams params = new ReplayParams();
                params.setModuleId(moduleInfo.getId());
                params.setAppName(appName);
                params.setTraceId(traceId);
                replayService.replay(params);
            }
        }
    }
}
