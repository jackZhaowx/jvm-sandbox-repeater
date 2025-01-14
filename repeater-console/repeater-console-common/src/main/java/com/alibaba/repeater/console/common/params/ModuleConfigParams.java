package com.alibaba.repeater.console.common.params;

import lombok.Getter;
import lombok.Setter;

/**
 * {@link ModuleConfigParams}
 * <p>
 *
 * @author zhaowanxin
 */
@Getter
@Setter
public class ModuleConfigParams extends BaseParams {

    private String appName;

    private String environment;

    private String config;
}
