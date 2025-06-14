package com.miguoma.by.modules.system.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.hutool.core.comparator.CompareUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.base.service.impl.BaseServiceImpl;
import com.miguoma.by.common.exception.BaseException;
import com.miguoma.by.modules.system.convert.SysUserConvert;
import com.miguoma.by.modules.system.dto.SysUserDTO;
import com.miguoma.by.modules.system.entity.SysUser;
import com.miguoma.by.modules.system.mapper.SysUserMapper;
import com.miguoma.by.modules.system.query.SysUserQuery;
import com.miguoma.by.modules.system.service.SysUserRoleService;
import com.miguoma.by.modules.system.service.SysUserService;
import com.miguoma.by.modules.system.vo.SysUserVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户管理
 *
 * @author lliyuu520 lliyuu520@gmail.com
 */
@Service
@AllArgsConstructor
public class SysUserServiceImpl extends BaseServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    private final SysUserRoleService sysUserRoleService;

    /**
     * 分页查询
     *
     * @param query
     * @return
     */
    @Override
    public PageVO<SysUserVO> page(SysUserQuery query) {
        // 查询参数

        // 分页查询
        final IPage<SysUser> page = page(getPage(query), buildWrapper(query));

        // 数据列表

        return PageVO.of(SysUserConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
    }

    /**
     * 构建查询条件
     *
     * @param query
     * @return
     */
    private LambdaQueryWrapper<SysUser> buildWrapper(SysUserQuery query) {
        final LambdaQueryWrapper<SysUser> queryWrapper = Wrappers.lambdaQuery();
        final String username = query.getUsername();
        if (StrUtil.isNotBlank(username)) {
            queryWrapper.like(SysUser::getUsername, username);
        }
        queryWrapper.orderByAsc(SysUser::getId);
        return queryWrapper;

    }

    /**
     * 保存用户
     *
     * @param sysUserDTO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOne(SysUserDTO sysUserDTO) {
        SysUser entity = SysUserConvert.INSTANCE.convertFromDTO(sysUserDTO);

        // 判断用户名是否存在
        SysUser user = baseMapper.getByUsername(entity.getUsername());
        if (user != null) {
            throw new BaseException("用户名已经存在");
        }
        entity.setPassword(sysUserDTO.getPassword());


        // 保存用户
        baseMapper.insert(entity);

        // 保存用户角色关系
        sysUserRoleService.saveOrUpdate(entity.getId(), sysUserDTO.getRoleIdList());


    }

    /**
     * 编辑用户
     *
     * @param sysUserDTO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOne(SysUserDTO sysUserDTO) {

        final String username = sysUserDTO.getUsername();
        final String password = sysUserDTO.getPassword();
        final Long userId = sysUserDTO.getId();
        // 判断用户名是否存在
        SysUser userDB = baseMapper.getByUsername(username);
        if (userDB != null) {
            final Long userDBId = userDB.getId();
            final int compare = CompareUtil.compare(userDBId, userId);
            if (compare != 0) {
                throw new BaseException("用户名已经存在");
            }
        }
        final SysUser sysUser = getById(userId);
        sysUser.setPassword(SaSecureUtil.sha256(password));
        // 更新用户
        updateById(sysUser);

        // 更新用户角色关系
        final List<Long> roleIdList = sysUserDTO.getRoleIdList();
        sysUserRoleService.saveOrUpdate(userId, roleIdList);


    }

    /**
     * 删除用户
     *
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteOne(Long id) {
        // 删除用户
        removeById(id);
        // 删除用户角色关系
        sysUserRoleService.deleteByUserId(id);


    }

    /**
     * 修改密码
     *
     * @param id          用户ID
     * @param newPassword 新密码
     */
    @Override
    public void updatePassword(Long id, String newPassword) {
        // 修改密码
        SysUser user = getById(id);
        user.setPassword(newPassword);

        updateById(user);
    }


}
