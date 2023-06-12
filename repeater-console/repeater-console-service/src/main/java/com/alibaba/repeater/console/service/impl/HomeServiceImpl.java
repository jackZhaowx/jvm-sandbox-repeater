package com.alibaba.repeater.console.service.impl;

import com.alibaba.jvm.sandbox.repeater.plugin.domain.RepeaterResult;
import com.alibaba.repeater.console.common.domain.HomeBO;
import com.alibaba.repeater.console.common.domain.HomeSearchBO;
import com.alibaba.repeater.console.common.domain.RecordAndReplayEchartBO;
import com.alibaba.repeater.console.common.params.HomeParams;
import com.alibaba.repeater.console.common.utils.DateUtil;
import com.alibaba.repeater.console.dal.dao.HomeDao;
import com.alibaba.repeater.console.dal.model.RecordAndReplayEchart;
import com.alibaba.repeater.console.service.HomeService;
import com.alibaba.repeater.console.service.convert.RecordAndReplayConverter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("homeService")
public class HomeServiceImpl implements HomeService {
    @Resource
    private HomeDao homeDao;
    @Resource
    RecordAndReplayConverter recordAndReplayEchartConverter;

    @Override
    public HomeBO dataJson() {
        return homeDao.dataJson();
    }

    @Override
    public HomeSearchBO searchJson(HomeParams params) {
        if (StringUtils.isBlank(params.getStartDate()) || StringUtils.isBlank(params.getEndDate())) {
            Map<String, String> map = DateUtil.weekBeginningAndEnding();
            params.setStartDate(map.get("begin"));
            params.setEndDate(map.get("end"));
        }
        HomeSearchBO homeSearchBO = homeDao.searchJson(params);
        homeSearchBO.setReplayEchartBOS(homeDao.searchCharJson(params).stream().map(recordAndReplayEchartConverter::convert).collect(Collectors.toList()));
        return homeSearchBO;
    }
}
