package com.miguoma.by.modules.system.dto;

import com.miguoma.by.modules.system.enums.RandomTypeEnums;
import lombok.Data;

import java.io.Serializable;

/**
 * 编码规则明细数据传输对象
 */
@Data
public class SysCodeRuleDetailDTO implements Serializable {


   

    /**
     * 来源字段
     */
    private String sourceField;
    /**
     * 编码类型
     */
    private String encodeType;



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

    /**
     * 长度
     */
    private Long length;

    /**
     * 随机类型 {@link RandomTypeEnums}
     */
    private String randomType;

    /**
     * 偏移年份
     */
    private Integer offsetYears;
} 
