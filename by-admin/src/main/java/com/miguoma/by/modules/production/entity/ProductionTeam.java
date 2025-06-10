package com.miguoma.by.modules.production.entity;

import com.miguoma.by.common.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 生产班组
 *
 * @author liliangyu
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductionTeam extends BaseEntity {
    /**
     * 编码
     */
    private String code;
    /**
     * 名称
     */
    private String name;
}
