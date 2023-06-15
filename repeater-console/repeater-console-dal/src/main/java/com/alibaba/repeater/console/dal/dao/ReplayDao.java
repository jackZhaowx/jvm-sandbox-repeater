package com.alibaba.repeater.console.dal.dao;

import com.alibaba.repeater.console.common.domain.ReplayListBO;
import com.alibaba.repeater.console.common.params.ReplayParams;
import com.alibaba.repeater.console.dal.model.Record;
import com.alibaba.repeater.console.dal.model.Replay;
import com.alibaba.repeater.console.dal.repository.ReplayRepository;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
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
                    if (params.getRepeatId() != null && !params.getRepeatId().isEmpty()) {
                        predicates.add(cb.equal(root.<String>get("repeatId"), params.getRepeatId()));
                    }
                    if (StringUtils.isNotBlank(params.getRTraceId())) {
                        predicates.add(cb.equal(root.<Record>get("record").<String>get("traceId"), params.getRTraceId()));
                    }
                    if (StringUtils.isNotBlank(params.getReplayStatus())) {
                        if ("2".equals(params.getReplayStatus())) {
                            predicates.add(cb.equal(root.<Integer>get("status"), Integer.valueOf(params.getReplayStatus())));
                        } else {
                            predicates.add(cb.equal(root.<Integer>get("success"), Integer.valueOf(params.getReplayStatus())));
                        }
                    }
                    if (params.getReplayType() != null && !params.getReplayType().isEmpty()) {
                        predicates.add(cb.equal(root.<Integer>get("replayType"), Integer.valueOf(params.getReplayType())));
                    }
                    return cb.and(predicates.toArray(new Predicate[0]));
                },
                pageable
        );
    }

    public long selectCount(long recordId) {
        return replayRepository.count((root, query, cb) -> {
            List<Predicate> predicates = Lists.newArrayList();
            predicates.add(cb.equal(root.<Record>get("record").<String>get("id"), recordId));
            return cb.and(predicates.toArray(new Predicate[0]));
        });
    }

    public long selectSuccCount(Long recordId) {
        return replayRepository.count((root, query, cb) -> {
            List<Predicate> predicates = Lists.newArrayList();
            predicates.add(cb.equal(root.<Record>get("record").<String>get("id"), recordId));
            predicates.add(cb.equal(root.<Boolean>get("success"), true));
            return cb.and(predicates.toArray(new Predicate[0]));
        });
    }

    public void deleReplay(Long id) {
        replayRepository.deleteByRecordId(id);
    }
}
