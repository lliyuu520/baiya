package com.miguoma.by.modules.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.miguoma.by.common.base.mapper.IBaseMapper;
import com.miguoma.by.modules.system.entity.SysUser;
import com.miguoma.by.modules.system.query.SysUserQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 系统用户
 *
 * @author lliyuu520 lliyuu520@gmail.com
 */
@Repository
public interface SysUserMapper extends IBaseMapper<SysUser> {

    Page<SysUser> getList(SysUserQuery sysUserQuery);

    SysUser getById(@Param("id") Long id);

    List<SysUser> getRoleUserList(Map<String, Object> params);

    default SysUser getByUsername(final String username) {
        final LambdaQueryWrapper<SysUser> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysUser::getUsername, username);
        return selectOne(wrapper);
    }

    default SysUser getByMobile(final String mobile) {
        return this.selectOne(new QueryWrapper<SysUser>().eq("mobile", mobile));
    }
}
