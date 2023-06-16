package com.alibaba.repeater.console.dal.dao;

import com.alibaba.repeater.console.common.params.RecordParams;
import com.alibaba.repeater.console.common.utils.DateUtil;
import com.alibaba.repeater.console.dal.model.Record;
import com.alibaba.repeater.console.dal.model.Replay;
import com.alibaba.repeater.console.dal.repository.RecordRepository;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * {@link RecordDao}
 * <p>
 *
 * @author zhaowanxin
 */
@Component("recordDao")
public class RecordDao {

    @Resource
    private RecordRepository recordRepository;

    public Record insert(Record record) {
        return recordRepository.save(record);
    }

    public Record selectByAppNameAndTraceId(String appName, String traceId) {
        return recordRepository.findByAppNameAndTraceId(appName, traceId);
    }

    public Page<Record> selectByAppNameOrTraceId(@NotNull final RecordParams params) {
        Pageable pageable = new PageRequest(params.getPage() - 1, params.getSize(), new Sort(Sort.Direction.DESC, "id"));
        return recordRepository.findAll(
                (root, query, cb) -> {
                    List<Predicate> predicates = Lists.newArrayList();
                    if (StringUtils.isNotBlank(params.getAppName())) {
                        predicates.add(cb.equal(root.<String>get("appName"), params.getAppName()));
                    }
                    if (StringUtils.isNotBlank(params.getTraceId())) {
                        predicates.add(cb.equal(root.<String>get("traceId"), params.getTraceId()));
                    }
                    if (StringUtils.isNotBlank(params.getClientHost())) {
                        predicates.add(cb.equal(root.<String>get("clientHost"), params.getClientHost()));
                    }
                    if (StringUtils.isNotBlank(params.getUrl())) {
                        predicates.add(cb.equal(root.<String>get("url"), params.getUrl()));
                    }
                    if (StringUtils.isNotBlank(params.getReplayStatus())) {
                        Subquery<Replay> subQuery = query.subquery(Replay.class);
                        Root<Replay> subRoot = subQuery.from(Replay.class);
                        if ("0".equals(params.getReplayStatus())) {
                            //这里是子查询的条件，前者是自身的条件，后者是主表的关联条件，当然where方法的参
                            //数是个可变参数，可以根据自己需要加条件
                            subQuery.where(cb.equal(root.get("id"), subRoot.<Record>get("record").get("id")));
                            //这句话不加会报错，因为他不知道你子查询要查出什么字段
                            subQuery.select(subRoot.get("id"));
                            predicates.add(cb.not(cb.exists(subQuery)));
                        } else if ("1".equals(params.getReplayStatus())) {
                            subQuery.where(cb.equal(subRoot.<Boolean>get("success"), true), cb.equal(root.get("id"), subRoot.<Record>get("record").get("id")));
                            //这句话不加会报错，因为他不知道你子查询要查出什么字段
                            subQuery.select(subRoot.get("id"));
                            predicates.add(cb.exists(subQuery));
                        } else if ("2".equals(params.getReplayStatus())) {
                            subQuery.where(cb.equal(subRoot.<Boolean>get("success"), false), cb.equal(root.get("id"), subRoot.<Record>get("record").get("id")));
                            //这句话不加会报错，因为他不知道你子查询要查出什么字段
                            subQuery.select(subRoot.get("id"));
                            predicates.add(cb.exists(subQuery));
                        }
                    }
                    if (StringUtils.isNotBlank(params.getStartDate()) && StringUtils.isNotBlank(params.getEndDate())) {
                        predicates.add(cb.between(root.<Date>get("gmtCreate"), DateUtil.strYMDToDate(params.getStartDate()), DateUtil.strYMDToDate(params.getEndDate())));
                    }
                    return cb.and(predicates.toArray(new Predicate[0]));
                },
                pageable
        );
    }

    public List<Record> selectNotRepaly(String appName) {
        return recordRepository.selectNotRepaly(appName);
    }

    public void update(Record record) {
        recordRepository.saveAndFlush(record);
    }
}
