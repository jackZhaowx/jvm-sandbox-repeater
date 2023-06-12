package com.alibaba.repeater.console.dal.dao;

import com.alibaba.repeater.console.common.domain.HomeBO;
import com.alibaba.repeater.console.common.domain.HomeSearchBO;
import com.alibaba.repeater.console.dal.model.RecordAndReplayEchart;
import com.alibaba.repeater.console.common.params.HomeParams;
import com.alibaba.repeater.console.common.utils.DateUtil;
import com.alibaba.repeater.console.dal.model.Record;
import com.alibaba.repeater.console.dal.repository.ModuleInfoRepository;
import com.alibaba.repeater.console.dal.repository.RecordAndReplayRepository;
import com.alibaba.repeater.console.dal.repository.RecordRepository;
import com.alibaba.repeater.console.dal.repository.ReplayRepository;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component("homeDao")
public class HomeDao {
    @Resource
    private RecordRepository recordRepository;
    @Resource
    ReplayRepository replayRepository;
    @Resource
    ModuleInfoRepository moduleInfoRepository;
    @Resource
    RecordAndReplayRepository recordAndReplayRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public long getRecordTotal() {
        return recordRepository.count();
    }

    public long getReplayTotal() {
        return replayRepository.count();
    }

    public long getReplayResultTotal(Integer result) {
        return replayRepository.count((root, query, cb) -> {
            List<Predicate> predicates = Lists.newArrayList();
            predicates.add(cb.equal(root.<Integer>get("success"), result));
            return cb.and(predicates.toArray(new Predicate[0]));
        });
    }

    public HomeBO dataJson() {
        HomeBO homeBO = new HomeBO();
        homeBO.setAppTotal(moduleInfoRepository.appTotal());
        homeBO.setMachineTotal(moduleInfoRepository.machineTotal());
        homeBO.setServiceTotal(moduleInfoRepository.serviceTotal());
        return homeBO;
    }

    public HomeSearchBO searchJson(HomeParams params) {
        HomeSearchBO homeSearchBO = new HomeSearchBO();
        homeSearchBO.setRecordTotal(recordRepository.count((root, query, cb) -> {
            List<Predicate> predicates = Lists.newArrayList();
            if (StringUtils.isNotBlank(params.getAppName())) {
                predicates.add(cb.equal(root.<String>get("appName"), params.getAppName()));
            }
            if (StringUtils.isNotBlank(params.getIp())) {
                predicates.add(cb.equal(root.<String>get("host"), params.getIp()));
            }
            if (StringUtils.isNotBlank(params.getStartDate()) && StringUtils.isNotBlank(params.getEndDate())) {
                predicates.add(cb.between(root.<Date>get("gmtCreate"), DateUtil.strYMDToDate(params.getStartDate()), DateUtil.strYMDToDate(params.getEndDate())));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        }));
        homeSearchBO.setReplayTotal(replayRepository.count((root, query, cb) -> {
            List<Predicate> predicates = Lists.newArrayList();
            if (StringUtils.isNotBlank(params.getAppName())) {
                predicates.add(cb.equal(root.<Record>get("record").<String>get("appName"), params.getAppName()));
            }
            if (StringUtils.isNotBlank(params.getIp())) {
                predicates.add(cb.equal(root.<Record>get("record").<String>get("host"), params.getIp()));
            }
            if (StringUtils.isNotBlank(params.getStartDate()) && StringUtils.isNotBlank(params.getEndDate())) {
                predicates.add(cb.between(root.<Date>get("gmtCreate"), DateUtil.strYMDToDate(params.getStartDate()), DateUtil.strYMDToDate(params.getEndDate())));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        }));
        homeSearchBO.setSuccessTotal(replayRepository.count((root, query, cb) -> {
            List<Predicate> predicates = Lists.newArrayList();
            predicates.add(cb.equal(root.<Integer>get("success"), 1));
            if (StringUtils.isNotBlank(params.getAppName())) {
                predicates.add(cb.equal(root.<Record>get("record").<String>get("appName"), params.getAppName()));
            }
            if (StringUtils.isNotBlank(params.getIp())) {
                predicates.add(cb.equal(root.<Record>get("record").<String>get("host"), params.getIp()));
            }
            if (StringUtils.isNotBlank(params.getStartDate()) && StringUtils.isNotBlank(params.getEndDate())) {
                predicates.add(cb.between(root.<Date>get("gmtCreate"), DateUtil.strYMDToDate(params.getStartDate()), DateUtil.strYMDToDate(params.getEndDate())));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        }));
        homeSearchBO.setFailTotal(replayRepository.count((root, query, cb) -> {
            List<Predicate> predicates = Lists.newArrayList();
            predicates.add(cb.equal(root.<Integer>get("success"), 0));
            if (StringUtils.isNotBlank(params.getAppName())) {
                predicates.add(cb.equal(root.<Record>get("record").<String>get("appName"), params.getAppName()));
            }
            if (StringUtils.isNotBlank(params.getIp())) {
                predicates.add(cb.equal(root.<Record>get("record").<String>get("host"), params.getIp()));
            }
            if (StringUtils.isNotBlank(params.getStartDate()) && StringUtils.isNotBlank(params.getEndDate())) {
                predicates.add(cb.between(root.<Record>get("record").<Date>get("gmtCreate"), DateUtil.strYMDToDate(params.getStartDate()), DateUtil.strYMDToDate(params.getEndDate())));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        }));
        return homeSearchBO;
    }

    public List<RecordAndReplayEchart> searchCharJson(HomeParams params) {
        List<RecordAndReplayEchart> replayEcharts1 = recordAndReplayRepository.countRecordDate(params.getAppName(), params.getIp(), DateUtil.strYMDToDate(params.getStartDate()), DateUtil.strYMDToDate(params.getEndDate()));
        entityManager.clear();
        List<RecordAndReplayEchart> replayEcharts2 = recordAndReplayRepository.countRepalyDate(params.getAppName(), params.getIp(), DateUtil.strYMDToDate(params.getStartDate()), DateUtil.strYMDToDate(params.getEndDate()));
        List<String> strDates = DateUtil.weekDays(DateUtil.strYMDToDate(params.getStartDate()), DateUtil.strYMDToDate(params.getEndDate()), "yyyy-MM-dd");
        List<RecordAndReplayEchart> replayEcharts = new ArrayList<>();
        for (String strDate : strDates) {
            RecordAndReplayEchart recordAndReplayEchart = new RecordAndReplayEchart();
            recordAndReplayEchart.setStrDate(strDate);
            recordAndReplayEchart.setReplayTotal(0L);
            recordAndReplayEchart.setRecordTotal(0L);
            for (RecordAndReplayEchart replayEchart : replayEcharts1) {
                if (strDate.equals(replayEchart.getStrDate())) {
                    recordAndReplayEchart.setRecordTotal(replayEchart.getRecordTotal());
                    break;
                }
            }
            for (RecordAndReplayEchart replayEchart : replayEcharts2) {
                if (strDate.equals(replayEchart.getStrDate())) {
                    recordAndReplayEchart.setReplayTotal(replayEchart.getReplayTotal());
                    break;
                }
            }
            replayEcharts.add(recordAndReplayEchart);
        }
        return replayEcharts;
    }
}
