package com.alibaba.repeater.console.service;

import com.alibaba.jvm.sandbox.repeater.plugin.domain.RepeaterResult;
import com.alibaba.repeater.console.common.domain.HomeBO;
import com.alibaba.repeater.console.common.domain.HomeSearchBO;
import com.alibaba.repeater.console.common.params.HomeParams;

import java.util.List;

public interface HomeService {
    /**
     * 返回首页基础数据
     *
     * @return
     */
    HomeBO dataJson();

    /**
     * 返回首页查询数据
     *
     * @param params
     * @return
     */
    HomeSearchBO searchJson(HomeParams params);
}
