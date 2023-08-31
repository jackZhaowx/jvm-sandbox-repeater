package com.alibaba.repeater.console.dal.dao;

import com.alibaba.repeater.console.common.domain.LineChartBO;
import com.alibaba.repeater.console.common.domain.OnlineBO;
import com.alibaba.repeater.console.common.domain.SearchTop5BO;
import com.alibaba.repeater.console.common.utils.DateUtil;
import com.alibaba.repeater.console.common.utils.MapUtil;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component("creenDao")
public class CreenDao {
    @Resource
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public List<SearchTop5BO> screenTop5() {
        String sql = "select hour(r.gmt_create ) hourTime ,count(1) numCount,0.00 rate from record r where r.gmt_create>=DATE_FORMAT(CURDATE(), '%Y-%m-%d %00:%00:%00') group by hour(r.gmt_create ) order by numCount desc limit 5";
        Query query = entityManager.createNativeQuery(sql);
        //将结为Map,返回后的果转换map中的key是数据库表字段，如果用实体接收，使用@JsonAlias设置一下在反序列化时进行映射
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> maps = query.getResultList();
        //封装分页信息  PageImpl类实现了Page<T>接口
        return maps.stream().map(e -> {
            return MapUtil.mapConvertToObject(e, SearchTop5BO.class);
        }).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OnlineBO online() {
        OnlineBO onlineBO = new OnlineBO();
        String totalRecordCount = "select count(0) from record";
        onlineBO.setTotalRecord(((BigInteger) entityManager.createNativeQuery(totalRecordCount).getSingleResult()).longValue());
        String totalReplayCount = "select count(0) from replay";
        onlineBO.setTotalReplay(((BigInteger) entityManager.createNativeQuery(totalReplayCount).getSingleResult()).longValue());
        String yearCount = "select count(0) from record r where year (r.gmt_create)=year (now())";
        onlineBO.setYearNum(((BigInteger) entityManager.createNativeQuery(yearCount).getSingleResult()).longValue());
        Map<String, String> months = DateUtil.monthBeginningAndEnding();
        String monthCount = "select count(0) from record r where r.gmt_create between '" + months.get("begin") + "' and '" + months.get("end") + "'";
        onlineBO.setMonthNum(((BigInteger) entityManager.createNativeQuery(monthCount).getSingleResult()).longValue());
        Map<String, String> weeks = DateUtil.weekBeginningAndEnding();
        String weekCount = "select count(0) from record r where r.gmt_create between '" + weeks.get("begin") + "' and '" + weeks.get("end") + "'";
        onlineBO.setWeekNum(((BigInteger) entityManager.createNativeQuery(weekCount).getSingleResult()).longValue());
        Map<String, String> days = DateUtil.dayBeginningAndEnding();
        String dayCount = "select count(0) from record r where r.gmt_create between '" + days.get("begin") + "' and '" + days.get("end") + "'";
        onlineBO.setDayNum(((BigInteger) entityManager.createNativeQuery(dayCount).getSingleResult()).longValue());
        String replayRateQuery = "select concat(round((select count(0) from replay r )/(select count(0) from record r) * 100,0),'%') from dual";
        onlineBO.setReplayRate(entityManager.createNativeQuery(replayRateQuery).getSingleResult().toString());
        String succRateQuery = "select concat(round((select count(0) from replay r where r.success=1)/(select count(0) from replay  r where r.success is not null) * 100,0),'%') from dual";
        onlineBO.setSuccRate(entityManager.createNativeQuery(succRateQuery).getSingleResult().toString());
        String failRateQuery = "select concat(round((select count(0) from replay r where r.success=0)/(select count(0) from replay  r where r.success is not null) * 100,0),'%') from dual";
        onlineBO.setFailRate(entityManager.createNativeQuery(failRateQuery).getSingleResult().toString());
        String oneTimeQuery = "select count(0) from record r where r.gmt_create between '" + days.get("begin") + "' and '" + days.get("end") + "' and r.cost <100";
        onlineBO.setOneTime(((BigInteger) entityManager.createNativeQuery(oneTimeQuery).getSingleResult()).longValue());
        String fiveTimeQuery = "select count(0) from record r where r.gmt_create between '" + days.get("begin") + "' and '" + days.get("end") + "' and r.cost <500";
        onlineBO.setFiveTime(((BigInteger) entityManager.createNativeQuery(fiveTimeQuery).getSingleResult()).longValue());
        String nineTimeQuery = "select count(0) from record r where r.gmt_create between '" + days.get("begin") + "' and '" + days.get("end") + "' and r.cost <900";
        onlineBO.setNineTime(((BigInteger) entityManager.createNativeQuery(nineTimeQuery).getSingleResult()).longValue());
        String tenTimeQuery = "select count(0) from record r where r.gmt_create between '" + days.get("begin") + "' and '" + days.get("end") + "' and r.cost <1000";
        double one = ((BigInteger) entityManager.createNativeQuery(tenTimeQuery).getSingleResult()).doubleValue();
        String twoTimeQuery = "select count(0) from record r where r.gmt_create between '" + days.get("begin") + "' and '" + days.get("end") + "' and r.cost >=1000 and r.cost <2000";
        double two = ((BigInteger) entityManager.createNativeQuery(twoTimeQuery).getSingleResult()).doubleValue();
        NumberFormat format = NumberFormat.getInstance();
        format.setMaximumFractionDigits(2);
        onlineBO.setOneT(new double[]{Double.parseDouble(format.format(one / (onlineBO.getDayNum()==0?1:onlineBO.getDayNum())))});
        onlineBO.setOneG(new double[]{Double.parseDouble(format.format(two /  (onlineBO.getDayNum()==0?1:onlineBO.getDayNum())))});
        String yearReplayCount = "select count(0) from record r where year (r.gmt_create)=year (now()) and exists(select 1 from replay r2 where r2.record_id=r.id)";
        onlineBO.setYearReplay(((BigInteger) entityManager.createNativeQuery(yearReplayCount).getSingleResult()).longValue());
        String yearReplaySuccCount = "select count(0) from record r where year (r.gmt_create)=year (now()) and exists(select 1 from replay r2 where r2.record_id=r.id and r2.success=1)";
        onlineBO.setYearSuccReplay(((BigInteger) entityManager.createNativeQuery(yearReplaySuccCount).getSingleResult()).longValue());
        String monthReplayCount = "select count(0) from record r where r.gmt_create between '" + months.get("begin") + "' and '" + months.get("end") + "' and exists(select 1 from replay r2 where r2.record_id=r.id)";
        onlineBO.setMonthReplay(((BigInteger) entityManager.createNativeQuery(monthReplayCount).getSingleResult()).longValue());
        String monthReplaySuccCount = "select count(0) from record r where r.gmt_create between '" + months.get("begin") + "' and '" + months.get("end") + "' and exists(select 1 from replay r2 where r2.record_id=r.id and r2.success=1)";
        onlineBO.setMonthSuccReplay(((BigInteger) entityManager.createNativeQuery(monthReplaySuccCount).getSingleResult()).longValue());
        String dayReplayCount = "select count(0) from record r where r.gmt_create between '" + days.get("begin") + "' and '" + days.get("end") + "' and exists(select 1 from replay r2 where r2.record_id=r.id)";
        onlineBO.setDayReplay(((BigInteger) entityManager.createNativeQuery(dayReplayCount).getSingleResult()).longValue());
        String dayReplaySuccCount = "select count(0) from record r where r.gmt_create between '" + days.get("begin") + "' and '" + days.get("end") + "' and exists(select 1 from replay r2 where r2.record_id=r.id and r2.success=1)";
        onlineBO.setDaySuccReplay(((BigInteger) entityManager.createNativeQuery(dayReplaySuccCount).getSingleResult()).longValue());
        String sql = "select hour(r.gmt_create ) hourT ,count(1) numCount from record r where r.gmt_create>=DATE_FORMAT(CURDATE(), '%Y-%m-%d %00:%00:%00') group by hour(r.gmt_create )";
        Query lineChartQuery = entityManager.createNativeQuery(sql);
        //将结为Map,返回后的果转换map中的key是数据库表字段，如果用实体接收，使用@JsonAlias设置一下在反序列化时进行映射
        lineChartQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> maps = lineChartQuery.getResultList();
        //封装分页信息  PageImpl类实现了Page<T>接口
        List<LineChartBO> lineChartBOS = maps.stream().map(e -> {
            return MapUtil.mapConvertToObject(e, LineChartBO.class);
        }).collect(Collectors.toList());
        int[] xData = new int[lineChartBOS.size()];
        int[] dataArr = new int[lineChartBOS.size()];
        for (int i = 0; i < lineChartBOS.size(); i++) {
            xData[i] = lineChartBOS.get(i).getHourT();
            dataArr[i] = lineChartBOS.get(i).getNumCount();
        }
        onlineBO.setXData(xData);
        onlineBO.setDataArr(dataArr);
        return onlineBO;
    }
}
