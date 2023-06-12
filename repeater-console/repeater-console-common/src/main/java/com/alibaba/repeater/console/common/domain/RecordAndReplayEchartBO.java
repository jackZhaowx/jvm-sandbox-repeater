package com.alibaba.repeater.console.common.domain;

import lombok.Data;

@Data
public class RecordAndReplayEchartBO extends BaseBO{
    private String strDate;
    private long recordTotal;
    private long replayTotal;
}
