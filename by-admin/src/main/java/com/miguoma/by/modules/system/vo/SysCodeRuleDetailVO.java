package com.miguoma.by.modules.system.vo;

import com.miguoma.by.modules.system.entity.SysCodeRuleDetail;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 编码规则明细视图对象
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysCodeRuleDetailVO extends SysCodeRuleDetail implements Comparable<Integer> {

    /**
     * 排序规则 由小到大排序
     */
    @Override
    public int compareTo(Integer o) {
        return this.getWeight().compareTo(o);
    }
}
