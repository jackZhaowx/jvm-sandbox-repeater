package com.alibaba.repeater.console.dal.dao;

import com.alibaba.repeater.console.common.params.ModuleInfoParams;
import com.alibaba.repeater.console.dal.model.ModuleInfo;
import com.alibaba.repeater.console.dal.repository.ModuleInfoRepository;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * {@link }
 * <p>
 *
 * @author zhaowanxin
 */
@Component("moduleInfoDao")
public class ModuleInfoDao {

    @Resource
    private ModuleInfoRepository moduleInfoRepository;

    public List<ModuleInfo> findByAppName(String appName) {
        return moduleInfoRepository.findByAppName(appName);
    }

    public Page<ModuleInfo> selectByParams(@NotNull final ModuleInfoParams params) {
        Pageable pageable = new PageRequest(params.getPage() - 1, params.getSize(), new Sort(Sort.Direction.DESC, "id"));
        return moduleInfoRepository.findAll(
                (root, query, cb) -> {
                    List<Predicate> predicates = Lists.newArrayList();
                    if (params.getAppName() != null && !params.getAppName().isEmpty()) {
                        predicates.add(cb.equal(root.<String>get("appName"), params.getAppName()));
                    }
                    if (params.getIp() != null && !params.getIp().isEmpty()) {
                        predicates.add(cb.equal(root.<String>get("ip"), params.getIp()));
                    }
                    if (params.getEnvironment() != null && !params.getEnvironment().isEmpty()) {
                        predicates.add(cb.equal(root.<String>get("environment"), params.getEnvironment()));
                    }
                    return cb.and(predicates.toArray(new Predicate[0]));
                },
                pageable
        );
    }

    public ModuleInfo save(ModuleInfo params) {
        if (moduleInfoRepository.updateByAppNameAndIpAndEnviAndEnvironment(params) > 0) {
            return params;
        }
        return moduleInfoRepository.saveAndFlush(params);
    }

    public ModuleInfo saveAndFlush(ModuleInfo params) {
        return moduleInfoRepository.saveAndFlush(params);
    }

    public ModuleInfo findByAppNameAndIpAndEnvironment(String appName, String ip, String environment) {
        return moduleInfoRepository.findByAppNameAndIpAndEnvironment(appName, ip, environment);
    }

    public ModuleInfo findOneById(Long id) {
        return moduleInfoRepository.findById(id);
    }

    public void updateIngoreKeys(ModuleInfoParams params) {
        moduleInfoRepository.updateIngoreKeys(params);
    }

    public List<ModuleInfo> queryRepeat(String appName) {
        return moduleInfoRepository.findAll(
                (root, query, cb) -> {
                    List<Predicate> predicates = Lists.newArrayList();
                    if (StringUtils.isNotBlank(appName)) {
                        predicates.add(cb.equal(root.<String>get("appName"), appName));
                    }
                    predicates.add(cb.equal(root.<String>get("environment"), "repeate"));
                    return cb.and(predicates.toArray(new Predicate[0]));
                }
        );
    }

    public ModuleInfo queryByIdAndAppName(String appName, long moduleId) {
        ModuleInfo moduleInfo = moduleInfoRepository.findByIdAndAppName(moduleId, appName);
        return moduleInfo;
    }

    public List<String> getRecordModule() {
        return moduleInfoRepository.getRecordModule();
    }
}
