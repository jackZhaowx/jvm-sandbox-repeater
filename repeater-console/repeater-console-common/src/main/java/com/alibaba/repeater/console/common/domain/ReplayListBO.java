package com.alibaba.repeater.console.common.domain;

import lombok.Data;

import java.util.Date;

@Data
public class ReplayListBO extends BaseBO {


    private String appName;

    private String ip;

    private String environment;

    private String repeatId;

    private String rTraceId;

    private String url;

    private String entranceDesc;

    private String traceId;

    private Integer status;

    private Boolean success;

    private Date gmtCreate;

    private Long cost;

    private String replayType;
}
