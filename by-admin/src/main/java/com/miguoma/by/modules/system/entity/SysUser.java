package com.miguoma.by.modules.system.entity;

import com.miguoma.by.common.base.entity.BaseEntity;
import com.miguoma.by.common.enums.UserTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户
 *
 * @author lliyuu520 lliyuu520@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysUser extends BaseEntity {
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;




}
