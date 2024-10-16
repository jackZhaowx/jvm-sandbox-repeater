package com.alibaba.repeater.console.service.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.jvm.sandbox.repeater.plugin.core.util.LogUtil;
import com.alibaba.repeater.console.common.Constants;
import com.alibaba.repeater.console.common.params.ReplayParams;
import com.alibaba.repeater.console.dal.dao.ModuleInfoDao;
import com.alibaba.repeater.console.dal.model.ModuleInfo;
import com.alibaba.repeater.console.dal.model.Record;
import com.alibaba.repeater.console.service.ReplayService;
import com.google.common.eventbus.Subscribe;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;

@Component
public class ReplayRecordListener {
    @Resource
    private ReplayService replayService;
    @Resource
    private ModuleInfoDao moduleInfoDao;

    /**
     * 回放
     *
     * @param record
     */
    @Subscribe
    public void replay(Record record) {
        LogUtil.info("触发了event事件回放：{}", record.getTraceId());
        if (record != null) {
            String appName = record.getAppName();
            List<ModuleInfo> moduleInfos = moduleInfoDao.queryRepeat(appName);
            if (moduleInfos != null && moduleInfos.size() > 0) {
                Random random = new Random();
                //遍历执行回放
                String traceId = record.getTraceId();
                //查询本条记录的回放机器（多个机器随机选择一个）
                ModuleInfo moduleInfo = moduleInfos.get(random.nextInt(moduleInfos.size()));
                ReplayParams params = new ReplayParams();
                params.setModuleId(moduleInfo.getId());
                params.setAppName(appName);
                params.setTraceId(traceId);
                params.setReplayType(Constants.REPLAY_TYPE_EVENT + "");
                LogUtil.info("开始执行回放：");
                replayService.replay(params);
            }
        }
    }
}
