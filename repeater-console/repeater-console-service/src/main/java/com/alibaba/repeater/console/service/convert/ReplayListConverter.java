package com.alibaba.repeater.console.service.convert;

import com.alibaba.repeater.console.common.domain.ReplayListBO;
import com.alibaba.repeater.console.dal.model.Replay;
import org.springframework.stereotype.Component;

/**
 * {@link replayListConverter}
 * <p>
 *
 * @author zhaowanxin
 */
@Component("replayListConverter")
public class ReplayListConverter implements ModelConverter<Replay, ReplayListBO> {
    @Override
    public ReplayListBO convert(Replay replay) {
        ReplayListBO rbo = new ReplayListBO();
        rbo.setGmtCreate(replay.getGmtCreate());
        rbo.setAppName(replay.getAppName());
        rbo.setIp(replay.getIp());
        rbo.setEnvironment(replay.getEnvironment());
        rbo.setRepeatId(replay.getRepeatId());
        rbo.setTraceId(replay.getTraceId());
        rbo.setSuccess(replay.getSuccess());
        rbo.setCost(replay.getCost());
        return rbo;
    }


    @Override
    public Replay reconvert(ReplayListBO target) {
        return null;
    }
}