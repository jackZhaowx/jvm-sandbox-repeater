package com.alibaba.repeater.console.common.domain;

import lombok.Data;

import java.util.List;

@Data
public class CreenBO extends BaseBO {
    private List<SearchTop5BO> searchTop5BOS;
    private OnlineBO onlineBO;
}
