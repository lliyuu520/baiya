package com.miguoma.by.modules.system.vo;

import com.miguoma.by.modules.system.entity.SysUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 用户
 *
 * @author lliyuu520 lliyuu520@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserVO extends SysUser {


    private List<Long> roleIdList;


}
