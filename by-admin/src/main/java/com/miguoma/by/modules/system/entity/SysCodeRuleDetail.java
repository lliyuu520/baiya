package com.miguoma.by.modules.system.entity;

import com.miguoma.by.common.base.entity.BaseEntity;
import com.miguoma.by.modules.system.enums.EncodeTypeEnums;
import com.miguoma.by.modules.system.enums.RuleTypeEnums;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 编码规则明细
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysCodeRuleDetail extends BaseEntity {

    /**
     * 规则ID
     */
    private Long ruleId;

    /**
     * 编码类型 {@link EncodeTypeEnums}
     */
    private String encodeType;


    /**
     * 来源字段
     */
    private String sourceField;

    /**
     * 排序号
     */
    private Integer weight;

    /**
     * 规则类型 {@link RuleTypeEnums}
     */
    private String ruleType;

    /**
     * 开始索引(从0开始)
     */
    private Integer indexBegin;
    
    /**
     *结束索引(-1代表最后一个)
     */
    private Integer indexEnd;
    /**
     * 值
     */
    private String constant;

}
