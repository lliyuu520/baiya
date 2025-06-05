package com.miguoma.by.modules.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.miguoma.by.common.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 角色菜单关系
 *
 * @author lliyuu520 lliyuu520@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_role_field")
public class SysRoleField extends BaseEntity {
    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 表配置ID
     */
    private Long tableFieldId;
    

}
