package com.alibaba.repeater.console.dal.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class RecordAndReplayEchart implements Serializable {
    @Id
    private String strDate;

    @Column(name = "record_total")
    private long recordTotal;

    @Column(name = "replay_total")
    private long replayTotal;
}
