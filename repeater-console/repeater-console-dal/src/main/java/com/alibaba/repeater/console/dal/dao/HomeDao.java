package com.alibaba.repeater.console.dal.dao;

import com.alibaba.repeater.console.dal.model.Replay;
import com.alibaba.repeater.console.dal.repository.RecordRepository;
import com.alibaba.repeater.console.dal.repository.ReplayRepository;
import com.google.common.collect.Lists;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Component("homeDao")
public class HomeDao {
    @Resource
    private RecordRepository recordRepository;
    @Resource
    ReplayRepository replayRepository;

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
}
