package com.alibaba.repeater.console.dal.repository;

import com.alibaba.repeater.console.common.domain.HomeBO;
import com.alibaba.repeater.console.common.exception.BizException;
import com.alibaba.repeater.console.common.params.HomeParams;
import com.alibaba.repeater.console.common.params.ModuleInfoParams;
import com.alibaba.repeater.console.dal.model.ModuleInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * {@link ModuleInfoRepository}
 * <p>
 *
 * @author zhaowanxin
 */
@Repository
@Transactional(rollbackFor = {RuntimeException.class, Error.class, BizException.class})
public interface ModuleInfoRepository extends JpaRepository<ModuleInfo, Long>, JpaSpecificationExecutor<ModuleInfo> {

    /**
     * 根据appName查找在线模块
     *
     * @param appName 应用名
     * @return
     */
    List<ModuleInfo> findByAppName(String appName);


    @Modifying
    @Query(
            "update ModuleInfo set gmtModified =  :#{#moduleInfo.gmtModified} , namespace = :#{#moduleInfo.namespace}, port = :#{#moduleInfo.port}, version = :#{#moduleInfo.version},repeateMode = :#{#moduleInfo.repeateMode}, status = :#{#moduleInfo.status}" +
                    " where appName =  :#{#moduleInfo.appName}" +
                    " and ip = :#{#moduleInfo.ip}" + " and environment = :#{#moduleInfo.environment}"
    )
    int updateByAppNameAndIpAndEnviAndEnvironment(@Param("moduleInfo") ModuleInfo moduleInfo);

    ModuleInfo findByAppNameAndIpAndEnvironment(String appName, String ip, String environment);

    ModuleInfo findById(Long id);

    @Modifying
    @Query(
            "update ModuleInfo set ingoreKeys =  :#{#moduleInfo.ingoreKeys} " +
                    " where id =  :#{#moduleInfo.id}"
    )
    int updateIngoreKeys(@Param("moduleInfo") ModuleInfoParams moduleInfo);

    ModuleInfo findByIdAndAppName(long id, String appName);

    @Query(value = "select app_name from module_info where environment<>'repeate'", nativeQuery = true)
    List<String> getRecordModule();

    @Query(value = "select count(1) from (select app_name from module_info group by app_name) tt", nativeQuery = true)
    long appTotal();

    @Query(value = "select count(1) from (select app_name,ip from module_info group by app_name,ip) tt", nativeQuery = true)
    long machineTotal();

    @Query(value = "select count(1) from (select app_name,ip,port from module_info group by app_name,ip,port) tt", nativeQuery = true)
    long serviceTotal();
}
