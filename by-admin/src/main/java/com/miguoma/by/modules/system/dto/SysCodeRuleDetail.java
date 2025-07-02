package com.miguoma.by.modules.system.dto;

import com.miguoma.by.modules.system.enums.RandomTypeEnums;
import lombok.Data;

import java.io.Serializable;

/**
 * 规则明细
 */
@Data
public class SysCodeRuleDetail implements Serializable {


    /**
     * 编码类型 {@link com.miguoma.by.common.enums.BaseEncodeEnums}
     */
    private String encodeType;

    /**
     * 来源字段
     */
    private String sourceField;


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

    private String specifyBoxNo;

}