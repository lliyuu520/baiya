package com.miguoma.by.modules.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.base.service.impl.BaseServiceImpl;
import com.miguoma.by.common.cache.RollPermissionCache;
import com.miguoma.by.common.satoken.user.UserDetail;
import com.miguoma.by.common.utils.SysUserUtil;
import com.miguoma.by.modules.system.convert.SysRoleConvert;
import com.miguoma.by.modules.system.dto.SysRoleDTO;
import com.miguoma.by.modules.system.entity.SysRole;
import com.miguoma.by.modules.system.entity.SysRoleField;
import com.miguoma.by.modules.system.mapper.SysMenuMapper;
import com.miguoma.by.modules.system.mapper.SysRoleFieldMapper;
import com.miguoma.by.modules.system.mapper.SysRoleMapper;
import com.miguoma.by.modules.system.query.SysRoleQuery;
import com.miguoma.by.modules.system.service.SysRoleMenuService;
import com.miguoma.by.modules.system.service.SysRoleService;
import com.miguoma.by.modules.system.service.SysUserRoleService;
import com.miguoma.by.modules.system.vo.SysRoleVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 系统角色服务实现类
 * 实现角色相关的业务逻辑，包括角色的增删改查、权限管理等
 *
 * @author lliyuu520 lliyuu520@gmail.com
 */
@Service
@AllArgsConstructor
public class SysRoleServiceImpl extends BaseServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    /**
     * 角色菜单关联服务
     */
    private final SysRoleMenuService sysRoleMenuService;
    /**
     * 用户角色关联服务
     */
    private final SysUserRoleService sysUserRoleService;
    /**
     * 菜单数据访问层
     */
    private final SysMenuMapper sysMenuMapper;
    /**
     * 角色权限缓存
     */
    private final RollPermissionCache rollPermissionCache;
    /**
     * 角色字段权限关系数据访问层
     */
    private final SysRoleFieldMapper sysRoleFieldMapper;

    /**
     * 分页查询角色列表
     *
     * @param query 角色查询条件
     * @return 分页结果
     */
    @Override
    public PageVO<SysRoleVO> page(SysRoleQuery query) {
        IPage<SysRole> page = baseMapper.selectPage(getPage(query), getWrapper(query));
        return PageVO.of(SysRoleConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
    }

    /**
     * 获取角色列表
     *
     * @param query 角色查询条件
     * @return 角色列表
     */
    @Override
    public List<SysRoleVO> getList(SysRoleQuery query) {
        List<SysRole> entityList = baseMapper.selectList(getWrapper(query));
        return SysRoleConvert.INSTANCE.convertList(entityList);
    }

    /**
     * 构建查询条件
     *
     * @param query 角色查询条件
     * @return 查询条件包装器
     */
    private Wrapper<SysRole> getWrapper(SysRoleQuery query) {
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(query.getName()), SysRole::getName, query.getName());
        return wrapper;
    }

    /**
     * 保存角色信息
     * 1. 保存角色基本信息
     * 2. 保存角色菜单关联关系
     * 3. 更新角色权限缓存
     *
     * @param dto 角色数据传输对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysRoleDTO dto) {
        SysRole entity = SysRoleConvert.INSTANCE.convertFromDTO(dto);
        // 保存角色
        baseMapper.insert(entity);
        // 保存角色菜单关系
        final Long roleId = entity.getId();
        sysRoleMenuService.saveOrUpdate(roleId, dto.getMenuIdList());
        List<String> rolePermission = getRollPermission(roleId);
        rollPermissionCache.setPermission(roleId, rolePermission);
    }

    /**
     * 更新角色信息
     * 1. 更新角色基本信息
     * 2. 更新角色菜单关联关系
     * 3. 更新角色权限缓存
     *
     * @param dto 角色数据传输对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysRoleDTO dto) {
        SysRole entity = SysRoleConvert.INSTANCE.convertFromDTO(dto);
        // 更新角色
        updateById(entity);
        // 更新角色菜单关系
        final Long roleId = entity.getId();
        sysRoleMenuService.saveOrUpdate(roleId, dto.getMenuIdList());
        List<String> rolePermission = getRollPermission(roleId);
        rollPermissionCache.setPermission(roleId, rolePermission);
    }

    /**
     * 删除角色
     * 1. 删除角色基本信息
     * 2. 删除用户角色关联关系
     * 3. 删除角色菜单关联关系
     * 4. 删除角色权限缓存
     *
     * @param id 角色ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteOne(Long id) {
        // 删除角色
        removeById(id);
        // 删除用户角色关系
        sysUserRoleService.deleteByRoleId(id);
        // 删除角色菜单关系
        sysRoleMenuService.deleteByRoleId(id);
        rollPermissionCache.deletePermission(id);
    }

    /**
     * 获取角色权限列表
     * 1. 从数据库获取角色权限
     * 2. 处理权限字符串，转换为列表
     *
     * @param roleId 角色ID
     * @return 权限列表
     */
    @Override
    public List<String> getRollPermission(Long roleId) {
        List<String> roleAuthorityList = sysMenuMapper.getRoleAuthorityList(roleId);
        List<String> list = new ArrayList<>();
        roleAuthorityList.forEach(m -> {
            List<String> split = StrUtil.split(m, StrUtil.COMMA);
            List<String> collect = split.stream().filter(StrUtil::isNotBlank).toList();
            list.addAll(collect);
        });
        return list;
    }

    /**
     * 获取当前用户的菜单权限集合
     * 1. 获取用户角色列表
     * 2. 从缓存获取角色权限
     * 3. 如果缓存不存在，则从数据库获取并更新缓存
     * 4. 合并所有角色的权限并去重
     *
     * @return 权限集合
     */
    @Override
    public List<String> getPermission() {
        UserDetail userInfo = SysUserUtil.getUserInfo();
        List<Long> roleIdList = userInfo.getRoleIdList();
        List<String> permissionList = CollUtil.newArrayList();
        roleIdList.forEach(roleId -> {
            // 先从缓存中获取
            List<String> permission = rollPermissionCache.getPermission(roleId);
            if (CollUtil.isEmpty(permission)) {
                // 缓存中没有，从数据库中获取
                permission = getRollPermission(roleId);
                rollPermissionCache.setPermission(roleId, permission);
            }
            permissionList.addAll(permission);
        });
        return new HashSet<>(permissionList).stream().toList();
    }

    /**
     * 保存角色字段权限
     *
     * @param dto
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveField(SysRoleDTO dto) {
        Long id = dto.getId();
        List<Long> fieldList = dto.getTableFieldIdList();
        if (CollUtil.isEmpty(fieldList)) {
            return;
        }
        // 删除原有的字段权限
        sysRoleFieldMapper.deleteByRoleId(id);
        // 保存新的字段权限
        fieldList.forEach(m -> {
            SysRoleField sysRoleField = new SysRoleField();
            sysRoleField.setRoleId(id);
            sysRoleField.setTableFieldId(m);
            sysRoleFieldMapper.insert(sysRoleField);
        });

    }

    /**
     * 获取角色字段权限
     *
     * @param roleId 角色ID
     * @return 角色字段权限列表
     */
    @Override
    public List<Long> getFieldByRoleId(Long roleId) {
        return sysRoleFieldMapper.getFieldIdList(roleId);
    }

    /**
     * @param roleIdList
     * @return
     */
    @Override
    public List<String> getFieldPermission(List<Long> roleIdList) {
        if (CollUtil.isNotEmpty(roleIdList)) {
            List<String> list = new ArrayList<>();
            roleIdList.forEach(roleId -> {
                List<String> fieldPermission = sysRoleFieldMapper.getPermissionByRoleId(roleId);
                list.addAll(fieldPermission);
            });
            // 去重 转set再转回来
            return new HashSet<>(list).stream().toList();
        }
        return List.of();
    }
}
