package com.alibaba.repeater.console.service;

import com.alibaba.jvm.sandbox.repeater.plugin.domain.RepeaterResult;
import com.alibaba.repeater.console.common.domain.ModuleInfoBO;
import com.alibaba.repeater.console.common.domain.PageResult;
import com.alibaba.repeater.console.common.params.ModuleInfoParams;
import com.alibaba.repeater.console.dal.model.ModuleInfo;

import java.util.List;

/**
 * {@link ModuleInfoService}
 * <p>
 *
 * @author zhaowanxin
 */
public interface ModuleInfoService {

    PageResult<ModuleInfoBO> query(ModuleInfoParams params);

    RepeaterResult<List<ModuleInfoBO>> query(String appName);

    RepeaterResult<ModuleInfoBO> query(String appName, String ip, String environment);

    RepeaterResult<ModuleInfoBO> report(ModuleInfoBO params);

    RepeaterResult<ModuleInfoBO> active(ModuleInfoParams params);

    RepeaterResult<ModuleInfoBO> frozen(ModuleInfoParams params);

    RepeaterResult<String> install(ModuleInfoParams params);

    RepeaterResult<String> reload(ModuleInfoParams params);

    ModuleInfo getOne(Long id);

    RepeaterResult<ModuleInfo> updateIngoreKeys(ModuleInfoParams params);

    RepeaterResult<List<ModuleInfoBO>> queryNotIpNotEnvironment(String appName, String ip, String environment);

    RepeaterResult<ModuleInfoBO> queryByIdAndAppName(String appName, long moduleId);
}
