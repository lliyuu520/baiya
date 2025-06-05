package com.miguoma.by.common.utils;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.SaTokenException;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.spring.SpringMVCUtil;
import cn.dev33.satoken.stp.StpUtil;
import com.miguoma.by.common.exception.BaseException;
import com.miguoma.by.common.satoken.user.UserDetail;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * 系统用户工具类
 *
 * @author liliangyu
 */
@UtilityClass
@Slf4j
public class SysUserUtil {

    /**
     * 获取用户信息
     *
     * @return
     */
    public UserDetail getUserInfo() {
        if (!SpringMVCUtil.isWeb()) {
            final UserDetail userInfo = ThreadLocalSysUserUtil.getUserInfo();
            if (userInfo != null) {
                return userInfo;
            }
            return new UserDetail();
        }
        try {
            final SaSession session = StpUtil.getSession();
            if (session == null) {
                return new UserDetail();
            }
            return (UserDetail) session.get("user");

        } catch (final NotLoginException e1) {
            return new UserDetail();
        } catch (final SaTokenException e2) {
            throw new BaseException(403, "没有权限，禁止访问");
        } catch (final Exception e3) {
            throw new BaseException("服务器异常");
        }

    }

    /**
     * 保存用户信息
     *
     * @param userDetail
     */
    public void setUserInfo(final UserDetail userDetail) {
        StpUtil.getSession().set("user", userDetail);
    }


    /**
     * 登出用户
     *
     * @param sysUserId
     */
    public void logout(final Long sysUserId) {
        StpUtil.logout(sysUserId);
    }


}
