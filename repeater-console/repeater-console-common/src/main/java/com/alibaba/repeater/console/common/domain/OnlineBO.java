package com.alibaba.repeater.console.common.domain;

import lombok.Data;

@Data
public class OnlineBO extends BaseBO {
    private long totalRecord;
    private long totalReplay;
    private long yearNum;
    private long monthNum;
    private long weekNum;
    private long dayNum;
    private String replayRate;
    private String succRate;
    private String failRate;
    private long oneTime;
    private long fiveTime;
    private long nineTime;
    private double[] oneG;
    private double[] oneT;
    private long yearReplay;
    private long yearSuccReplay;
    private long monthReplay;
    private long monthSuccReplay;
    private long dayReplay;
    private long daySuccReplay;
    private int[] xData;
    private int[] dataArr;
}
