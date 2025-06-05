package com.miguoma.by.modules.system.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 编码规则明细数据传输对象
 */
@Data
public class SysCodeRuleDetailDTO implements Serializable {


    /**
     * 编码类型
     */
    private String encodeType;

    /**
     * 来源类型
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
     * 开始索引(1代表第一个)
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
