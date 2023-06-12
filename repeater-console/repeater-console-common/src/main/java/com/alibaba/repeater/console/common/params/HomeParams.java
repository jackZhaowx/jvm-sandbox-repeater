package com.alibaba.repeater.console.common.params;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HomeParams extends BaseParams {
    private String ip;
    private String startDate;
    private String endDate;
}
