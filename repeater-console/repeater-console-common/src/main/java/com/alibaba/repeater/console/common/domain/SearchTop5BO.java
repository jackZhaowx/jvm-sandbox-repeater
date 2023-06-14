package com.alibaba.repeater.console.common.domain;

import lombok.Data;

@Data
public class SearchTop5BO extends BaseBO {
    private String hourTime;
    private long numCount;
    private double rate = 0.00;
}
