package com.alibaba.repeater.console.service.convert;

import com.alibaba.repeater.console.common.domain.ModuleInfoBO;
import com.alibaba.repeater.console.common.domain.ModuleStatus;
import com.alibaba.repeater.console.common.domain.RecordAndReplayEchartBO;
import com.alibaba.repeater.console.dal.model.RecordAndReplayEchart;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component("rcordAndReplayConverter")
public class RecordAndReplayConverter implements ModelConverter<RecordAndReplayEchart, RecordAndReplayEchartBO> {
    @Override
    public RecordAndReplayEchartBO convert(RecordAndReplayEchart source) {
        RecordAndReplayEchartBO recordAndReplayEchartBO = new RecordAndReplayEchartBO();
        BeanUtils.copyProperties(source, recordAndReplayEchartBO);
        return recordAndReplayEchartBO;
    }

    @Override
    public RecordAndReplayEchart reconvert(RecordAndReplayEchartBO target) {
        return null;
    }
}
