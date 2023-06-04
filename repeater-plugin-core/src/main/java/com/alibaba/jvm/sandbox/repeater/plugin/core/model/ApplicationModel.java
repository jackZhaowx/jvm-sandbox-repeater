package com.alibaba.jvm.sandbox.repeater.plugin.core.model;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.alibaba.jvm.sandbox.repeater.plugin.Constants;
import com.alibaba.jvm.sandbox.repeater.plugin.domain.RepeaterConfig;
import com.alibaba.jvm.sandbox.repeater.plugin.core.util.ExceptionAware;
import org.apache.commons.lang3.StringUtils;


import static com.alibaba.jvm.sandbox.repeater.plugin.core.util.PropertyUtil.getPropertyOrDefault;
import static com.alibaba.jvm.sandbox.repeater.plugin.core.util.PropertyUtil.getSystemPropertyOrDefault;

/**
 * {@link ApplicationModel} 描述一个基础应用模型
 * <p>
 * 应用名    {@link ApplicationModel#appName}
 * 机器名    {@link ApplicationModel#host}
 * 环境信息  {@link ApplicationModel#environment}
 * 模块配置  {@link ApplicationModel#config}
 * </p>
 *
 * @author zhaowanxin
 */
public class ApplicationModel {

    private String appName;

    private String environment;

    private String repeateMode;

    private String namespance;

    private String host;

    private volatile RepeaterConfig config;

    private ExceptionAware ea = new ExceptionAware();

    private volatile boolean fusing = false;

    private static ApplicationModel instance = new ApplicationModel();

    private ApplicationModel() {
        this.appName = getPropertyOrDefault(Constants.APP_NAME, "");
        if (StringUtils.isEmpty(appName)) {
            appName = getSystemPropertyOrDefault(Constants.APP_NAME, "defaultAppName");
        }
        this.environment = getPropertyOrDefault(Constants.APP_ENV, "");
        if (StringUtils.isEmpty(environment)) {
            environment = getSystemPropertyOrDefault(Constants.APP_ENV, "defaultAppEnv");
        }
        this.repeateMode = getPropertyOrDefault(Constants.REPEAT_MODE, "");
        if (StringUtils.isEmpty(repeateMode)) {
            repeateMode = getSystemPropertyOrDefault(Constants.REPEAT_MODE, "0");
        }
        this.namespance = getPropertyOrDefault(Constants.NAMESPANCE, "");
        if (StringUtils.isEmpty(namespance)) {
            namespance = getSystemPropertyOrDefault(Constants.NAMESPANCE, "");
        }
        try {
            this.host = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            this.host = "127.0.0.1";
        }
    }

    public static ApplicationModel instance() {
        return instance;
    }

    /**
     * 是否正在工作（熔断机制）
     *
     * @return true/false
     */
    public boolean isWorkingOn() {
        return !fusing;
    }

    /**
     * 是否降级（系统行为）
     *
     * @return true/false
     */
    public boolean isDegrade() {
        return config == null || config.isDegrade();
    }

    /**
     * 异常阈值检测
     *
     * @param throwable 异常类型
     */
    public void exceptionOverflow(Throwable throwable) {
        if (ea.exceptionOverflow(throwable, config == null ? 1000 : config.getExceptionThreshold())) {
            fusing = true;
            ea.printErrorLog();
        }
    }

    public Integer getSampleRate() {
        return config == null ? 0 : config.getSampleRate();
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getRepeateMode() {
        return repeateMode;
    }

    public void setRepeateMode(String repeateMode) {
        this.repeateMode = repeateMode;
    }

    public String getNamespance() {
        return namespance;
    }

    public void setNamespance(String namespance) {
        this.namespance = namespance;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public RepeaterConfig getConfig() {
        return config;
    }

    public void setConfig(RepeaterConfig config) {
        this.config = config;
    }

    public ExceptionAware getEa() {
        return ea;
    }

    public void setEa(ExceptionAware ea) {
        this.ea = ea;
    }

    public boolean isFusing() {
        return fusing;
    }

    public void setFusing(boolean fusing) {
        this.fusing = fusing;
    }

}
