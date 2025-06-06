package com.miguoma.by.modules.system.service.impl;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.miguoma.by.common.base.service.impl.BaseServiceImpl;
import com.miguoma.by.common.constant.Constant;
import com.miguoma.by.common.exception.BaseException;
import com.miguoma.by.common.satoken.user.UserDetail;
import com.miguoma.by.modules.system.convert.SysMenuConvert;
import com.miguoma.by.modules.system.dto.SysMenuDTO;
import com.miguoma.by.modules.system.entity.SysMenu;
import com.miguoma.by.modules.system.mapper.SysMenuMapper;
import com.miguoma.by.modules.system.service.SysMenuService;
import com.miguoma.by.modules.system.service.SysRoleMenuService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 菜单管理
 *
 * @author lliyuu520 lliyuu520@gmail.com
 */
@Service
@AllArgsConstructor
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
    private final SysRoleMenuService sysRoleMenuService;

    /**
     * 保存菜单
     *
     * @param sysMenuDTO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOne(final SysMenuDTO sysMenuDTO) {
        final SysMenu entity = SysMenuConvert.INSTANCE.convertFromDTO(sysMenuDTO);

        // 保存菜单
        save(entity);
    }

    /**
     * 更新菜单
     *
     * @param sysMenuDTO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOne(final SysMenuDTO sysMenuDTO) {
        final SysMenu entity = SysMenuConvert.INSTANCE.convertFromDTO(sysMenuDTO);

        // 上级菜单不能为自己
        if (entity.getId().equals(entity.getParentId())) {
            throw new BaseException("上级菜单不能为自己");
        }

        // 更新菜单
        this.updateById(entity);
    }

    /**
     * 删除菜单
     *
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteOne(final Long id) {
        // 删除菜单
        baseMapper.deleteMenu(id);

        // 删除角色菜单关系
        this.sysRoleMenuService.deleteByMenuId(id);
    }

    /**
     * 菜单树
     *
     * @param type 菜单类型
     * @return
     */
    @Override
    public List<Tree<Long>> getMenuList(final Integer type) {
        final LambdaQueryWrapper<SysMenu> wrapper = Wrappers.lambdaQuery();
        if (type != null) {

            wrapper.eq(SysMenu::getType, type);
        }

        final List<SysMenu> menuList = list(wrapper);
        return getTreeList(menuList);

    }

    /**
     * 菜单树
     *
     * @param user 用户
     * @param type 菜单类型
     * @return
     */
    @Override
    public List<Tree<Long>> getUserMenuList(final UserDetail user, final Integer type) {
        final List<SysMenu> menuList;

        if (StrUtil.equals("admin", user.getUsername())) {
            return getMenuList(type);
        } else {
            menuList = baseMapper.getUserMenuList(user.getId(), type);
        }

        return getTreeList(menuList);
    }

    /**
     * 子菜单
     *
     * @param pid 父菜单ID
     * @return
     */
    @Override
    public Long getSubMenuCount(final Long pid) {
        return this.count(new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getParentId, pid));
    }

    /**
     * 用户权限列表
     *
     * @param user
     * @return
     */
    @Override
    public Set<String> getUserAuthority(final UserDetail user) {
        // 系统管理员，拥有最高权限
        final List<String> authorityList;
       
            authorityList = this.baseMapper.getUserAuthorityList(user.getId());

        // 用户权限列表
        final Set<String> permsSet = new HashSet<>();
        for (final String authority : authorityList) {
            if (StrUtil.isBlank(authority)) {
                continue;
            }
            permsSet.addAll(Arrays.asList(authority.trim().split(",")));
        }

        return permsSet;
    }

    /**
     * 构建树形菜单
     *
     * @param menuList
     * @return
     */
    private List<Tree<Long>> getTreeList(final List<SysMenu> menuList) {
        final List<TreeNode<Long>> treeNodeList = menuList.stream().map(m -> {
            final Long id = m.getId();
            String name = m.getName();
            final TreeNode<Long> longTreeNode = new TreeNode<>(id, m.getParentId(), name, m.getWeight());
            final HashMap<String, Object> map = new HashMap<>();
            map.put("perms", m.getPerms());
            map.put("icon", m.getIcon());
            map.put("openStyle", m.getOpenStyle());
            map.put("type", m.getType());
            map.put("url", m.getUrl());
            longTreeNode.setExtra(map);
            return longTreeNode;
        }).toList();

        return TreeUtil.build(treeNodeList, Constant.ROOT);
    }


}
