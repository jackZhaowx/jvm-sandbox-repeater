package com.alibaba.repeater.console.common.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * {@link RecordBO}
 * <p>
 *
 * @author zhaowanxin
 */
@Getter
@Setter
public class RecordBO extends BaseBO implements java.io.Serializable {

    private Long id;

    private Date gmtCreate;

    private Date gmtRecord;

    private String appName;

    private String environment;

    private String clientHost;

    private String url;

    private String host;

    private String traceId;

    private String entranceDesc;

    private boolean replay;

    private boolean replaySuccess;

    private long cost;

    @Override
    public String toString() {
        return super.toString();
    }
}
