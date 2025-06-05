package com.miguoma.by.modules.system.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 分配角色查询
 *
 * @author lliyuu520 lliyuu520@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysRoleUserQuery extends SysUserQuery {
    private Long roleId;

}
