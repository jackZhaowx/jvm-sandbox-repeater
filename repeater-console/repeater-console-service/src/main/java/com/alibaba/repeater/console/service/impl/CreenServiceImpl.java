package com.alibaba.repeater.console.service.impl;

import com.alibaba.repeater.console.common.domain.CreenBO;
import com.alibaba.repeater.console.dal.dao.CreenDao;
import com.alibaba.repeater.console.service.CreenService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("creenService")
public class CreenServiceImpl implements CreenService {
    @Resource
    private CreenDao creenDao;

    @Override
    public CreenBO creenSearch() {
        CreenBO creenBO = new CreenBO();
        creenBO.setSearchTop5BOS(creenDao.screenTop5());
        creenBO.setOnlineBO(creenDao.online());
        return creenBO;
    }
}
