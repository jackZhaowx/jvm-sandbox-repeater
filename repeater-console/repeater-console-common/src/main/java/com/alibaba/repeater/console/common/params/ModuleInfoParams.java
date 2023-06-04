package com.alibaba.repeater.console.common.params;

import lombok.Getter;
import lombok.Setter;

/**
 * {@link ModuleInfoParams}
 * <p>
 *
 * @author zhaowanxin
 */
@Getter
@Setter
public class ModuleInfoParams extends BaseParams {

    private Long id;
    private String ip;
    private String ingoreKeys;
}
