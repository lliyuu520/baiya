package com.miguoma.by.modules.system.service;

import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.base.service.BaseService;
import com.miguoma.by.modules.system.dto.SysUserDTO;
import com.miguoma.by.modules.system.entity.SysUser;
import com.miguoma.by.modules.system.query.SysUserQuery;
import com.miguoma.by.modules.system.vo.SysUserVO;

;

/**
 * 用户管理
 *
 * @author lliyuu520 lliyuu520@gmail.com
 */
public interface SysUserService extends BaseService<SysUser> {
    /**
     * 分页
     *
     * @param query
     * @return
     */
    PageVO<SysUserVO> page(SysUserQuery query);

    /**
     * 保存用户
     *
     * @param sysUserDTO
     */
    void saveOne(SysUserDTO sysUserDTO);

    /**
     * 更新用户
     *
     * @param sysUserDTO
     */
    void updateOne(SysUserDTO sysUserDTO);

    /**
     * 删除用户
     *
     * @param id
     */
    void deleteOne(Long id);


    /**
     * 修改密码
     *
     * @param id          用户ID
     * @param newPassword 新密码
     */
    void updatePassword(Long id, String newPassword);


}
