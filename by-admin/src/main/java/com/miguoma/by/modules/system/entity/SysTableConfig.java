package com.miguoma.by.modules.system.entity;

import com.miguoma.by.common.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 表配置
 *
 * @author liliangyu
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysTableConfig extends BaseEntity {


    /**
     * 表名
     */
    private String tableName;

    /**
     * 表描述
     */
    private String tableComment;


}
