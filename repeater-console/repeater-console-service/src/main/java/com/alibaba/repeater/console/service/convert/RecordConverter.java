package com.alibaba.repeater.console.service.convert;

import com.alibaba.repeater.console.common.domain.RecordBO;
import com.alibaba.repeater.console.dal.dao.ReplayDao;
import com.alibaba.repeater.console.dal.model.Record;
import com.alibaba.repeater.console.dal.model.Replay;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * {@link RecordConverter}
 * <p>
 *
 * @author zhaowanxin
 */
@Component("recordConverter")
public class RecordConverter implements ModelConverter<Record, RecordBO> {
    @Resource
    private ReplayDao replayDao;

    @Override
    public RecordBO convert(Record source) {
        RecordBO rb = new RecordBO();
        // lazy mode , this isn't a correct way to copy properties.
        BeanUtils.copyProperties(source, rb);
        if (source.getReplays() != null && source.getReplays().size() > 0) {
            Replay replay = source.getReplays().get(0);
            rb.setReplay(true);
            rb.setStatus(replay.getStatus());
            rb.setReplaySuccess(replay.getSuccess());
        } else {
            rb.setReplay(false);
            rb.setReplaySuccess(false);
        }
        return rb;
    }

    @Override
    public Record reconvert(RecordBO target) {
        return null;
    }
}
