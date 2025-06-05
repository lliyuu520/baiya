package com.miguoma.by.modules.system.entity;

import com.miguoma.by.common.base.entity.BaseEntity;

import com.miguoma.by.modules.system.enums.NumeralSystemEnums;
import com.miguoma.by.modules.system.enums.RuleTypeEnums;
import com.miguoma.by.modules.system.enums.SourceTypeEnums;
import com.miguoma.by.modules.system.enums.ValueDirectionEnums;
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
     * 编码类型 base62 ,base36 ,base10
     */
    private String encodeType;

    /**
     * 来源类型 {@link SourceTypeEnums}
     */
    private String sourceType;

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
