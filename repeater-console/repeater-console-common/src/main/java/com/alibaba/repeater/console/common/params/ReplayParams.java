package com.alibaba.repeater.console.common.params;

import lombok.*;

/**
 * {@link ReplayParams}
 * <p>
 *
 * @author zhaowanxin
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReplayParams extends BaseParams {

    private String ip;

    private String repeatId;

    private String port;

    private String namespace;

    private String ingoreKeys;

    private boolean mock;

    private long moduleId;

    private String rTraceId;

    private String replayStatus;

    private String replayType;

    private long recordId;

}
