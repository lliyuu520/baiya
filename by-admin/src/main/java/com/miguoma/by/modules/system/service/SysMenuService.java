package com.miguoma.by.modules.system.service;

import cn.hutool.core.lang.tree.Tree;
import com.miguoma.by.common.base.service.BaseService;
import com.miguoma.by.common.satoken.user.UserDetail;
import com.miguoma.by.modules.system.dto.SysMenuDTO;
import com.miguoma.by.modules.system.entity.SysMenu;

import java.util.List;
import java.util.Set;


/**
 * 菜单管理
 *
 * @author lliyuu520 lliyuu520@gmail.com
 */
public interface SysMenuService extends BaseService<SysMenu> {
    /**
     * 保存菜单
     *
     * @param sysMenuDTO
     */
    void saveOne(SysMenuDTO sysMenuDTO);

    /**
     * 更新菜单
     *
     * @param sysMenuDTO
     */
    void updateOne(SysMenuDTO sysMenuDTO);

    /**
     * 删除菜单
     *
     * @param id
     */
    void deleteOne(Long id);

    /**
     * 菜单列表
     *
     * @param type 菜单类型
     */
    List<Tree<Long>> getMenuList(Integer type);

    /**
     * 用户菜单列表
     *
     * @param user 用户
     * @param type 菜单类型
     */
    List<Tree<Long>> getUserMenuList(UserDetail user, Integer type);

    /**
     * 获取子菜单的数量
     *
     * @param pid 父菜单ID
     */
    Long getSubMenuCount(Long pid);

    /**
     * 获取用户权限列表
     */
    Set<String> getUserAuthority(UserDetail user);
}
