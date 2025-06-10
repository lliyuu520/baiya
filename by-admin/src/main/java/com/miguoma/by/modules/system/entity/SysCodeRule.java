package com.miguoma.by.modules.system.entity;

import com.miguoma.by.common.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 编码规则
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysCodeRule extends BaseEntity {
    /**
     * 规则名称
     */
    private String name;

    /**
     * 规则编码
     */
    private String code;
    /**
     * 二维码url前缀
     */
    private String qrCodeUrlPrefix;

    /**
     * 是否启用
     */
    private Boolean enabled;

}
