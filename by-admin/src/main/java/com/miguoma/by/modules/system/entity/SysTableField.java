package com.miguoma.by.modules.system.entity;

import com.miguoma.by.common.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字段配置
 *
 * @author liliangyu
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysTableField extends BaseEntity {


    /**
     * 表配置id
     */
    private Long tableConfigId;

    /**
     * 字段名称
     */
    private String fieldName;

    /**
     * 字段描述
     */
    private String fieldComment;

    /** 权限
     * 表名+字段名
    */
    private String permission;


}
