package com.alibaba.repeater.console.dal.dao;

import com.alibaba.repeater.console.common.domain.ReplayListBO;
import com.alibaba.repeater.console.common.params.ReplayParams;
import com.alibaba.repeater.console.dal.model.Replay;
import com.alibaba.repeater.console.dal.repository.ReplayRepository;
import com.google.common.collect.Lists;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.util.Date;
import java.util.List;

/**
 * {@link ReplayDao}
 * <p>
 *
 * @author zhaowanxin
 */
@Component("replayDao")
public class ReplayDao {

    @Resource
    private ReplayRepository replayRepository;

    public Replay save(Replay replay) {
        return replayRepository.save(replay);
    }

    public Replay saveAndFlush(Replay replay) {
        return replayRepository.saveAndFlush(replay);
    }

    public Replay findByRepeatId(String repeatId) {
        return replayRepository.findByRepeatId(repeatId);
    }

    public Page<Replay> selectByParams(ReplayParams params) {
        Pageable pageable = new PageRequest(params.getPage() - 1, params.getSize(), new Sort(Sort.Direction.DESC, "id"));
        return replayRepository.findAll(
                (root, query, cb) -> {
                    List<Predicate> predicates = Lists.newArrayList();
                    if (params.getAppName() != null && !params.getAppName().isEmpty()) {
                        predicates.add(cb.equal(root.<String>get("appName"), params.getAppName()));
                    }
                    if (params.getTraceId() != null && !params.getTraceId().isEmpty()) {
                        predicates.add(cb.equal(root.<String>get("traceId"), params.getTraceId()));
                    }
                    if (params.getRepeatId() != null && !params.getRepeatId().isEmpty()) {
                        predicates.add(cb.equal(root.<String>get("repeatId"), params.getRepeatId()));
                    }
                    if (params.getEnvironment() != null && !params.getEnvironment().isEmpty()) {
                        predicates.add(cb.equal(root.<String>get("environment"), params.getEnvironment()));
                    }
                    if (params.getIp() != null && !params.getIp().isEmpty()) {
                        predicates.add(cb.equal(root.<String>get("ip"), params.getIp()));
                    }
                    return cb.and(predicates.toArray(new Predicate[0]));
                },
                pageable
        );
    }
}
