package com.alibaba.jvm.sandbox.repeater.module;

import com.alibaba.fastjson.JSON;
import com.alibaba.jvm.sandbox.api.ModuleException;
import com.alibaba.jvm.sandbox.api.resource.ConfigInfo;
import com.alibaba.jvm.sandbox.api.resource.ModuleManager;
import com.alibaba.jvm.sandbox.repeater.plugin.core.model.ApplicationModel;
import com.alibaba.jvm.sandbox.repeater.plugin.core.util.HttpUtil;
import com.alibaba.jvm.sandbox.repeater.plugin.core.util.LogUtil;
import com.alibaba.jvm.sandbox.repeater.plugin.core.util.PropertyUtil;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.alibaba.jvm.sandbox.repeater.plugin.Constants.REPEAT_HEARTBEAT_URL;

/**
 * {@link HeartbeatHandler}
 * <p>
 *
 * @author zhaowanxin
 */
public class HeartbeatHandler {
    private final static Logger log = LoggerFactory.getLogger(HeartbeatHandler.class);
    private static final long FREQUENCY = 10;

    private final static String HEARTBEAT_DOMAIN = PropertyUtil.getPropertyOrDefault(REPEAT_HEARTBEAT_URL, "");

    private static ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,
            new BasicThreadFactory.Builder().namingPattern("heartbeat-pool-%d").daemon(true).build());

    private final ConfigInfo configInfo;
    private final ModuleManager moduleManager;
    private AtomicBoolean initialize = new AtomicBoolean(false);

    public HeartbeatHandler(ConfigInfo configInfo, ModuleManager moduleManager) {
        this.configInfo = configInfo;
        this.moduleManager = moduleManager;
    }

    public synchronized void start() {
        log.debug("start()....，上报心跳");
        if (initialize.compareAndSet(false, true)) {
            executorService.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    try {
                        innerReport();
                    } catch (Exception e) {
                        LogUtil.error("error occurred when report heartbeat", e);
                    }
                }
            }, 0, FREQUENCY, TimeUnit.SECONDS);
        }
    }

    public void stop() {
        if (initialize.compareAndSet(true, false)) {
            executorService.shutdown();
        }
    }

    private void innerReport() {
        log.debug("上报心跳");
        Map<String, String> params = new HashMap<String, String>(8);
        params.put("appName", ApplicationModel.instance().getAppName());
        params.put("environment", ApplicationModel.instance().getEnvironment());
        params.put("ip", ApplicationModel.instance().getHost());
        params.put("port", configInfo.getServerAddress().getPort() + "");
        params.put("moduleName", Constants.MODULE_ID);
        params.put("repeateMode", ApplicationModel.instance().getRepeateMode());
        params.put("namespace", configInfo.getNamespace());
        params.put("version", Constants.VERSION);
        try {
            params.put("status", moduleManager.isActivated(Constants.MODULE_ID) ? "ACTIVE" : "FROZEN");
        } catch (ModuleException e) {
            params.put("status", "FROZEN");
        }
        log.debug("上报参数={}",JSON.toJSONString(params));
        HttpUtil.doGet(HEARTBEAT_DOMAIN, params);
    }
}
