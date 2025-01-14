package com.alibaba.repeater.console.dal.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * {@link Record}
 * <p>
 *
 * @author zhaowanxin
 */
@Entity
@Table(name = "record")
@Getter
@Setter
public class Record implements java.io.Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "gmt_create")
    private Date gmtCreate;

    @Column(name = "gmt_record")
    private Date gmtRecord;

    @Column(name = "app_name")
    private String appName;

    private String environment;

    @Column(name = "client_host")
    private String clientHost;

    private String url;

    private String host;

    @Column(name = "trace_id")
    private String traceId;

    @Column(name = "entrance_desc")
    private String entranceDesc;

    @Column(name = "wrapper_record")
    private String wrapperRecord;

    private String request;

    private String response;

    private Long cost;

    private String source;

    @Column(name = "replay_type")
    private int replayType;

    @OneToMany(mappedBy = "record", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Replay> replays;
}
