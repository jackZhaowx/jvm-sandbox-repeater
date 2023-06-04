package com.alibaba.repeater.console.dal.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * {@link ModuleInfo}
 * <p>
 * 在线模块信息
 *
 * @author zhaowanxin
 */
@Entity
@Table(name = "module_info")
@Getter
@Setter
public class ModuleInfo implements java.io.Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "gmt_create")
    private Date gmtCreate;

    @Column(name = "gmt_modified")
    private Date gmtModified;

    @Column(name = "app_name")
    private String appName;

    private String environment;

    private String ip;

    private String port;

    @Column(name = "module_name")
    private String moduleName;

    @Column(name = "repeate_mode")
    private String repeateMode;

    private String namespace;

    private String version;

    private String status;

    @Column(name = "ingore_keys")
    private String ingoreKeys;
}
