package com.alibaba.repeater.console.dal.repository;

import com.alibaba.repeater.console.common.exception.BizException;
import com.alibaba.repeater.console.dal.model.Replay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * {@link }
 * <p>
 *
 * @author zhaowanxin
 */
@Repository
@Transactional(rollbackFor = {RuntimeException.class, Error.class, BizException.class})
public interface ReplayRepository extends JpaRepository<Replay, Long>, JpaSpecificationExecutor<Replay> {

    Replay findByRepeatId(String repeatId);

    Replay findByRecordId(long recordId);

    @Modifying
    @Query(value = "delete from replay where record_id=:record_id", nativeQuery = true)
    void deleteByRecordId(@Param("record_id") Long record_id);
}
