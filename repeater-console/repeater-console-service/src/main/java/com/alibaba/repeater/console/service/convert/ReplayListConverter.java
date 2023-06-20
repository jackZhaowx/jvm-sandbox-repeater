package com.alibaba.repeater.console.service.convert;

import com.alibaba.repeater.console.common.domain.ReplayListBO;
import com.alibaba.repeater.console.dal.model.Replay;
import org.springframework.stereotype.Component;

/**
 * {@link ReplayListConverter}
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
        rbo.setRTraceId(replay.getRecord().getTraceId());
        rbo.setUrl(replay.getRecord().getUrl());
        rbo.setEntranceDesc(replay.getRecord().getEntranceDesc());
        rbo.setReplayType(replay.getReplayType());
        return rbo;
    }


    @Override
    public Replay reconvert(ReplayListBO target) {
        return null;
    }
}
