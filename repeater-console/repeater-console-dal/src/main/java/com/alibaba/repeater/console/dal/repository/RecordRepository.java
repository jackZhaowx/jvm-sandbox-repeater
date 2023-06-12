package com.alibaba.repeater.console.dal.repository;

import com.alibaba.repeater.console.common.exception.BizException;
import com.alibaba.repeater.console.dal.model.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * {@link }
 * <p>
 *
 * @author zhaowanxin
 */
@Repository
@Transactional(rollbackFor = {RuntimeException.class, Error.class, BizException.class})
public interface RecordRepository extends JpaRepository<Record, Long>, JpaSpecificationExecutor<Record> {

    /**
     * 根据应用名和traceId索引
     *
     * @param appName 应用名
     * @param traceId traceId
     * @return 录制记录
     */
    Record findByAppNameAndTraceId(String appName, String traceId);

    @Query(value = "SELECT id, gmt_create, gmt_record, app_name, environment, client_host, url, host, trace_id, entrance_desc, wrapper_record, request, response, ingore_keys FROM record r WHERE app_name=:appName and not exists(select 1 from replay r2 where r2.record_id=r.id) order by id desc limit 50", nativeQuery = true)
    List<Record> selectNotRepaly(@Param("appName") String appName);

}
