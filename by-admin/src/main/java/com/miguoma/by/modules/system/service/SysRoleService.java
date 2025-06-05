package com.miguoma.by.modules.system.service;

import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.base.service.BaseService;
import com.miguoma.by.modules.system.dto.SysRoleDTO;
import com.miguoma.by.modules.system.entity.SysRole;
import com.miguoma.by.modules.system.query.SysRoleQuery;
import com.miguoma.by.modules.system.vo.SysRoleVO;

import java.util.List;

/**
 * 系统角色服务接口
 * 提供角色相关的业务功能，包括角色的增删改查、权限管理等
 *
 * @author lliyuu520 lliyuu520@gmail.com
 */
public interface SysRoleService extends BaseService<SysRole> {

    /**
     * 分页查询角色列表
     *
     * @param query 角色查询条件
     * @return 分页结果
     */
    PageVO<SysRoleVO> page(SysRoleQuery query);

    /**
     * 获取角色列表
     *
     * @param query 角色查询条件
     * @return 角色列表
     */
    List<SysRoleVO> getList(SysRoleQuery query);

    /**
     * 保存角色信息
     *
     * @param dto 角色数据传输对象
     */
    void save(SysRoleDTO dto);

    /**
     * 更新角色信息
     *
     * @param dto 角色数据传输对象
     */
    void update(SysRoleDTO dto);

    /**
     * 删除角色
     *
     * @param id 角色ID
     */
    void deleteOne(Long id);



    /**
     * 获取当前用户的菜单权限集合
     *
     * @return 菜单权限集合
     */
    List<String> getPermission();

    /**
     * 根据角色ID查询权限集合
     *
     * @param roleId 角色ID
     * @return 权限集合
     */
    List<String> getRollPermission(Long roleId);

    /**
     * 保存角色字段权限
     * 
     * @param dto
     */
    void saveField(SysRoleDTO dto);

    /**
     * 查询角色字段权限
     * @param id
     * @return
     */
    List<Long> getFieldByRoleId(Long id);

    /**
     *
     * @param roleIdList
     * @return
     */
    List<String> getFieldPermission(List<Long> roleIdList);
}
