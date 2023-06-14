package com.alibaba.repeater.console.service;

import com.alibaba.repeater.console.common.domain.CreenBO;
import com.alibaba.repeater.console.common.domain.SearchTop5BO;

import java.util.List;

public interface CreenService {
    /**
     * 查询今日流量top5
     *
     * @return
     */
    CreenBO creenSearch();
}
