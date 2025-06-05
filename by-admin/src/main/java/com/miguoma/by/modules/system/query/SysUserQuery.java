package com.miguoma.by.modules.system.query;

import com.miguoma.by.common.base.query.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 用户查询
 *
 * @author lliyuu520 lliyuu520@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysUserQuery extends BaseQuery {
    /**
     * 用户名
     */
    private String username;


}
