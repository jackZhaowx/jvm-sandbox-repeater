-- mcprd.module_config definition
CREATE DATABASE IF NOT EXISTS repeater
    DEFAULT CHARSET utf8
    COLLATE utf8_general_ci;
DROP TABLE IF EXISTS module_config;
CREATE TABLE `module_config` (
                                 `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                 `gmt_create` datetime NOT NULL COMMENT '创建时间',
                                 `gmt_modified` datetime NOT NULL COMMENT '录制时间',
                                 `app_name` varchar(255) NOT NULL COMMENT '应用名',
                                 `environment` varchar(255) NOT NULL COMMENT '环境信息',
                                 `config` longtext NOT NULL COMMENT '配置信息',
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='模块配置信息';


-- mcprd.module_info definition
DROP TABLE IF EXISTS module_info;
CREATE TABLE `module_info` (
                               `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                               `app_name` varchar(255) NOT NULL COMMENT '应用名',
                               `environment` varchar(255) NOT NULL COMMENT '环境信息',
                               `namespace` varchar(100) DEFAULT NULL COMMENT '命名空间',
                               `ip` varchar(36) NOT NULL COMMENT '机器IP',
                               `port` varchar(12) NOT NULL COMMENT '端口号',
                               `module_name` varchar(255) NOT NULL COMMENT '模块名',
                               `repeate_mode` varchar(2) NOT NULL COMMENT '回放模式，0-本机，1-远程',
                               `version` varchar(128) NOT NULL COMMENT '模块版本号',
                               `status` varchar(36) NOT NULL COMMENT '模块状态',
                               `gmt_create` datetime NOT NULL COMMENT '创建时间',
                               `gmt_modified` datetime NOT NULL COMMENT '修改时间',
                               `ingore_keys` longtext COMMENT '忽略的节点',
                               PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='在线模块信息';


-- mcprd.record definition
DROP TABLE IF EXISTS record;
CREATE TABLE `record` (
                          `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                          `gmt_create` datetime NOT NULL COMMENT '创建时间',
                          `gmt_record` datetime NOT NULL COMMENT '录制时间',
                          `app_name` varchar(255) NOT NULL COMMENT '应用名',
                          `environment` varchar(255) NOT NULL COMMENT '环境信息',
                          `client_host` varchar(36) DEFAULT NULL COMMENT '客户机器IP',
                          `url` varchar(500) DEFAULT NULL COMMENT '接口url',
                          `host` varchar(36) NOT NULL COMMENT '机器IP',
                          `trace_id` varchar(32) NOT NULL COMMENT '链路追踪ID',
                          `entrance_desc` varchar(2000) NOT NULL COMMENT '链路追踪ID',
                          `wrapper_record` longtext NOT NULL COMMENT '记录序列化信息',
                          `request` longtext NOT NULL COMMENT '请求参数JSON',
                          `response` longtext NOT NULL COMMENT '返回值JSON',
                          `ingore_keys` longtext COMMENT '忽略的节点',
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='录制信息';


-- mcprd.replay definition
DROP TABLE IF EXISTS replay;
CREATE TABLE `replay` (
                          `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                          `gmt_create` datetime NOT NULL COMMENT '创建时间',
                          `gmt_modified` datetime NOT NULL COMMENT '修改时间',
                          `app_name` varchar(255) NOT NULL COMMENT '应用名',
                          `environment` varchar(255) NOT NULL COMMENT '环境信息',
                          `ip` varchar(36) NOT NULL COMMENT '机器IP',
                          `repeat_id` varchar(32) NOT NULL COMMENT '回放ID',
                          `status` tinyint(4) NOT NULL COMMENT '回放状态',
                          `trace_id` varchar(32) DEFAULT NULL COMMENT '链路追踪ID',
                          `cost` bigint(20) DEFAULT NULL COMMENT '回放耗时',
                          `diff_result` longtext COMMENT 'diff结果',
                          `response` longtext COMMENT '回放结果',
                          `mock_invocation` longtext COMMENT 'mock过程',
                          `success` bit(1) DEFAULT NULL COMMENT '是否回放成功',
                          `record_id` bigint(20) DEFAULT NULL COMMENT '外键',
                          `ingore_keys` longtext COMMENT '忽略的节点',
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='回放信息';


-- mcprd.`role` definition
DROP TABLE IF EXISTS role;
CREATE TABLE `role` (
                        `role_id` varchar(30) COLLATE utf8_bin NOT NULL COMMENT '角色ID',
                        `role_name` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '角色名称',
                        `create_date` datetime NOT NULL COMMENT '创建日期',
                        `create_user` varchar(30) COLLATE utf8_bin NOT NULL COMMENT '创建用户',
                        `modify_date` datetime NOT NULL COMMENT '更新日期',
                        `modify_user` varchar(30) COLLATE utf8_bin NOT NULL COMMENT '更新人',
                        PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8  COMMENT='角色表';


-- mcprd.user_info definition
DROP TABLE IF EXISTS user_info;
CREATE TABLE `user_info` (
                             `user_id` varchar(30) COLLATE utf8_bin NOT NULL COMMENT '用户ID',
                             `user_nick_name` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '用户昵称',
                             `password` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '密码',
                             `mobile` varchar(30) COLLATE utf8_bin NOT NULL COMMENT '手机号',
                             `email` varchar(70) COLLATE utf8_bin NOT NULL COMMENT '邮箱',
                             `create_date` datetime NOT NULL COMMENT '创建日期',
                             `create_user` varchar(30) COLLATE utf8_bin NOT NULL COMMENT '创建用户',
                             `modify_date` datetime NOT NULL COMMENT '更新日期',
                             `modify_user` varchar(30) COLLATE utf8_bin NOT NULL COMMENT '更新人',
                             PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8  COMMENT='用户信息表';


-- mcprd.user_role definition
DROP TABLE IF EXISTS user_role;
CREATE TABLE `user_role` (
                             `user_id` varchar(30) COLLATE utf8_bin NOT NULL COMMENT '用户ID',
                             `role_id` varchar(30) COLLATE utf8_bin NOT NULL COMMENT '角色ID',
                             `create_date` datetime NOT NULL COMMENT '创建日期',
                             `create_user` varchar(30) COLLATE utf8_bin NOT NULL COMMENT '创建用户',
                             `modify_date` datetime NOT NULL COMMENT '更新日期',
                             `modify_user` varchar(30) COLLATE utf8_bin NOT NULL COMMENT '更新人',
                             PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8  COMMENT='用户角色表';
