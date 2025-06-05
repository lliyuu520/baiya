package com.miguoma.by.modules.system.vo;

import com.miguoma.by.modules.system.entity.SysMenu;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 菜单管理
 *
 * @author lliyuu520 lliyuu520@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysMenuVO extends SysMenu {
    
    /**
     * 父级名称
     */
    private String parentName;
   
}
