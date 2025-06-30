package com.miguoma.by.modules.system.entity;

import com.miguoma.by.common.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 系统日志实体
 *
 * @author AI Assistant
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysLog extends BaseEntity {

    /**
     * 操作模块
     */
    private String moduleName;

    /**
     * 操作类型
     */
    private String typeName;

    /**
     * 请求参数
     */
    private String requestParams;

    /**
     * 响应结果
     */
    private String responseResult;

    /**
     * 操作人用户名
     */
    private String operatorName;

    /**
     * 操作时间
     */
    private LocalDateTime operateTime;

    /**
     * 操作状态（0正常 1异常）
     */
    private Integer status;

    /**
     * 错误信息
     */
    private String errorMsg;

}
