package com.miguoma.by.modules.system.entity;

import com.miguoma.by.common.base.entity.BaseEntity;
import com.miguoma.by.modules.system.enums.RandomTypeEnums;
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
     * 编码类型 {@link EncodeConverUtils}
     */
    private String encodeType;

    /**
     * 来源字段
     */
    private String sourceField;

    /**
     * 规则类型 {@link RuleTypeEnums}
     */
    private String ruleType;

    /**
     * 开始索引(从0开始)
     */
    private Integer indexBegin;

    /**
     * 结束索引(-1代表最后一个)
     */
    private Integer indexEnd;
    /**
     * 值
     */
    private String constant;

    /**
     * 长度
     */
    private Integer length;

    /**
     * 随机类型 {@link RandomTypeEnums}
     */
    private String randomType;

    /**
     * 偏移年份
     */
    private Integer offsetYears;
    /**
     * 特殊箱号
     */
    private String specifyBoxNo;

}
