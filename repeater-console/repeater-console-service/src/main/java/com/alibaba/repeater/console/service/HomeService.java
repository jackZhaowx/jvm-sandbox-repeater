package com.alibaba.repeater.console.service;

import com.alibaba.jvm.sandbox.repeater.plugin.domain.RepeaterResult;
import com.alibaba.repeater.console.common.domain.HomeBO;

import java.util.List;

public interface HomeService {
    /**
     * 返回首页数据
     *
     * @return
     */
    RepeaterResult<List<HomeBO>> indexJson();
}
