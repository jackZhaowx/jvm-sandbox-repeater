package com.alibaba.repeater.console.common.domain;

import lombok.Data;

import java.util.Date;

@Data
public class ReplayListBO extends BaseBO {


    private String appName;

    private String ip;

    private String environment;

    private String repeatId;

    private String traceId;

    private Boolean success;

    private Date gmtCreate;

    private Long cost;
}
