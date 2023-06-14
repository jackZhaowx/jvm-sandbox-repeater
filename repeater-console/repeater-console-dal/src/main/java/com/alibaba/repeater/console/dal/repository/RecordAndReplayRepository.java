package com.alibaba.repeater.console.dal.repository;

import com.alibaba.repeater.console.dal.model.RecordAndReplayEchart;
import com.alibaba.repeater.console.common.exception.BizException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
@Transactional(rollbackFor = {RuntimeException.class, Error.class, BizException.class})
public interface RecordAndReplayRepository extends JpaRepository<RecordAndReplayEchart, String>, JpaSpecificationExecutor<RecordAndReplayEchart> {
    @Query(value = "select DATE_FORMAT(r.gmt_create , '%Y-%m-%d') 'str_date',count(1) 'record_total',0 'replay_total' from record r where" +
            " if(?1 is not null and ?1!='',app_name=?1,1=1 ) " +
            "and if(?2 is not null and ?2!='',host=?2,1=1 ) " +
            "and if(?3 is not null and ?4 is not null,gmt_create between ?3 " +
            "and ?4,1=1 ) group by DATE_FORMAT(r.gmt_create , '%Y-%m-%d')", nativeQuery = true)
    List<RecordAndReplayEchart> countRecordDate(@Param("appName") String appName, @Param("ip") String ip, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query(value = "select DATE_FORMAT(r.gmt_create , '%Y-%m-%d') 'str_date',count(1) 'replay_total',0 'record_total'  from record r where" +
            " if(?1 is not null and ?1!='',app_name=?1,1=1 ) " +
            "and if(?2 is not null and ?2!='',host=?2,1=1 ) " +
            "and if(?3 is not null and ?4 is not null,gmt_create between ?3 " +
            "and ?4,1=1 ) and exists(select 1 from replay r2 where r.id=r2.record_id)  group by DATE_FORMAT(r.gmt_create , '%Y-%m-%d')", nativeQuery = true)
    List<RecordAndReplayEchart> countRepalyDate(@Param("appName") String appName, @Param("ip") String ip, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
