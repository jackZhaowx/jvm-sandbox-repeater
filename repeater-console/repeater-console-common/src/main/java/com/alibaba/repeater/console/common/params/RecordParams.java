package com.alibaba.repeater.console.common.params;

import lombok.*;

import java.util.Date;

/**
 * {@link RecordParams}
 * <p>
 *
 * @author zhaowanxin
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
public class RecordParams extends BaseParams {
    private String clientHost;
    private String url;
    private String host;
    private Date startDate;
    private Date endDate;

    public RecordParams(String clientHost, String url, String host, Date startDate, Date endDate) {
        this.clientHost = clientHost;
        this.url = url;
        this.host = host;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
