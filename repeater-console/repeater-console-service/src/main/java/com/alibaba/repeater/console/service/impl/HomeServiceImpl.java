package com.alibaba.repeater.console.service.impl;

import com.alibaba.jvm.sandbox.repeater.plugin.domain.RepeaterResult;
import com.alibaba.repeater.console.common.domain.HomeBO;
import com.alibaba.repeater.console.dal.dao.HomeDao;
import com.alibaba.repeater.console.service.HomeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("homeService")
public class HomeServiceImpl implements HomeService {
    @Resource
    private HomeDao homeDao;
    @Override
    public RepeaterResult<List<HomeBO>> indexJson() {
        List<HomeBO> result = new ArrayList<>();
        HomeBO homeBO = new HomeBO();
        homeBO.setName("原始记录");
        homeBO.setTotal(homeDao.getRecordTotal());
        HomeBO homeBO1 = new HomeBO();
        homeBO1.setName("回调记录");
        homeBO1.setTotal(homeDao.getReplayTotal());
        HomeBO homeBO2 = new HomeBO();
        homeBO2.setName("相同结果");
        homeBO2.setTotal(homeDao.getReplayResultTotal(1));
        HomeBO homeBO3 = new HomeBO();
        homeBO3.setName("不同结果");
        homeBO3.setTotal(homeDao.getReplayResultTotal(0));
        result.add(homeBO);
        result.add(homeBO1);
        result.add(homeBO2);
        result.add(homeBO3);
        return RepeaterResult.builder().success(true).message("查询成功").data(result).build();
    }
}
