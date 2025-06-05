package com.miguoma.by.modules.system.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 编码规则数据传输对象
 */
@Data
public class SysCodeRuleDTO implements Serializable {

    /**
     * ID
     */
    private Long id;

    /**
     * 规则名称
     */
    private String name;

    /**
     * 规则编码
     */
    private String code;


    /**
     * 箱码规则详情
     */
    private List<SysCodeRuleDetailDTO> boxCodeRuleList;
    
    
    /**
     * 袋码规则详情
     */
    private List<SysCodeRuleDetailDTO> bagCodeRuleList;
    
    /**
     * 万用码规则详情
     */
    private List<SysCodeRuleDetailDTO> universalCodeRuleList;

}
