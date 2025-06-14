package com.miguoma.by.modules.system.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.miguoma.by.common.exception.BaseException;
import com.miguoma.by.common.satoken.user.UserDetail;
import com.miguoma.by.common.utils.SysUserUtil;
import com.miguoma.by.modules.system.dto.SysAccountLoginDTO;
import com.miguoma.by.modules.system.entity.SysUser;
import com.miguoma.by.modules.system.service.SysAuthService;
import com.miguoma.by.modules.system.service.SysUserRoleService;
import com.miguoma.by.modules.system.service.SysUserService;
import com.miguoma.by.modules.system.vo.SysTokenVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 权限认证服务
 *
 * @author lliyuu520 lliyuu520@gmail.com
 */
@Service
@AllArgsConstructor
@Slf4j
public class SysAuthServiceImpl implements SysAuthService {

    private final SysUserService sysUserService;
    private final SysUserRoleService sysUserRoleService;

    /**
     * 账号密码登录
     *
     * @param sysAccountLoginDTO 登录信息
     * @return
     */
    @Override
    public SysTokenVO loginByAccount(final SysAccountLoginDTO sysAccountLoginDTO) {
        final String username = sysAccountLoginDTO.getUsername();
        final String password = sysAccountLoginDTO.getPassword();
        
        final LambdaQueryWrapper<SysUser> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysUser::getUsername, username);
        wrapper.eq(SysUser::getPassword, password);
        final SysUser sysUser = sysUserService.getOne(wrapper);
        if (sysUser == null) {
            log.error("用户名或密码错误:\n{}\n{}", username, password);
            throw new BaseException("用户名或密码错误");
        }
        final UserDetail userDetail = UserDetail.of(sysUser);
        final Long userId = userDetail.getId();
        final List<Long> roleList = sysUserRoleService.getRoleIdList(userId);
        userDetail.setRoleIdList(roleList);
        StpUtil.login(userDetail.getId());
        SysUserUtil.setUserInfo(userDetail);
        final SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        return new SysTokenVO(tokenInfo.getTokenValue());
    }

    /**
     * 退出登录
     */
    @Override
    public void logout() {
        SysUserUtil.logout(SysUserUtil.getUserInfo().getId());

    }
}
