package com.alibaba.repeater.console.common.domain;

import lombok.Data;

import java.util.List;

@Data
public class HomeSearchBO {
    private long recordTotal;
    private long replayTotal;
    private long successTotal;
    private long failTotal;
    List<RecordAndReplayEchartBO> replayEchartBOS;
}
