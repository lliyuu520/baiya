package com.miguoma.by.modules.system.query;

import com.miguoma.by.common.base.query.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 编码规则查询对象
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysCodeRuleQuery extends BaseQuery {

    /**
     * 编码规则名称
     */
    private String name;

    /**
     * 编码规则编码
     */
    private String code;

  
}
