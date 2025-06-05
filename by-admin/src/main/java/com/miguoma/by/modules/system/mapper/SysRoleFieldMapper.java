package com.miguoma.by.modules.system.mapper;

import com.miguoma.by.common.base.mapper.IBaseMapper;
import com.miguoma.by.modules.system.entity.SysRoleField;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色字段权限关系
 *
 * @author lliyuu520 lliyuu520@gmail.com
 */
@Repository
public interface SysRoleFieldMapper extends IBaseMapper<SysRoleField> {

    /**
     * 根据角色ID，获取字段ID列表
     *
     * @param roleId 角色ID
     * @return 字段ID列表
     */
    List<Long> getFieldIdList(@Param("roleId") Long roleId);


    /**
     * 根据角色ID，获取字段权限
     *
     * @param roleId 角色ID
     * @return 字段权限列表
     */
    List<String> getPermissionByRoleId(@Param("roleId") Long roleId);


    /**根据roleId 物理删除
     * 
     * @param roleId 角色ID
     */
    void deleteByRoleId(@Param("roleId") Long roleId);
}
