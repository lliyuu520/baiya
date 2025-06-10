package com.miguoma.by.modules.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.miguoma.by.common.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@TableName("sys_log")
@EqualsAndHashCode(callSuper = true)
public class SysLog extends BaseEntity {

    /**
     * 操作模块
     */
    private String module;

    /**
     * 操作类型
     */
    private String type;

    /**
     * 操作描述
     */
    private String description;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 请求参数
     */
    private String params;

    /**
     * 请求IP
     */
    private String ip;

    /**
     * 请求URL
     */
    private String url;

    /**
     * 操作人ID
     */
    private Long operatorId;

    /**
     * 操作人用户名
     */
    private String operatorName;

    /**
     * 操作时间
     */
    private LocalDateTime operateTime;

    /**
     * 执行时长(毫秒)
     */
    private Long duration;

    /**
     * 操作状态（0正常 1异常）
     */
    private Integer status;

    /**
     * 错误信息
     */
    private String errorMsg;
}
