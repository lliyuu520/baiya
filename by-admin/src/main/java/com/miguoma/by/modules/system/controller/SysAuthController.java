package com.miguoma.by.modules.system.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import com.miguoma.by.common.utils.Result;
import com.miguoma.by.modules.system.dto.SysAccountLoginDTO;
import com.miguoma.by.modules.system.service.SysAuthService;
import com.miguoma.by.modules.system.vo.SysTokenVO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 认证管理
 *
 * @author lliyuu520 lliyuu520@gmail.com
 */
@RestController
@RequestMapping("/sys/auth")
@AllArgsConstructor
public class SysAuthController {
    private final SysAuthService sysAuthService;

    /**
     * 普通用户登录
     *
     * @param sysAccountLoginDTO
     * @return
     */
    @PostMapping("login")
    @SaIgnore
    public Result<SysTokenVO> login(@RequestBody final SysAccountLoginDTO sysAccountLoginDTO) {
        final SysTokenVO token = this.sysAuthService.loginByAccount(sysAccountLoginDTO);

        return Result.ok(token);
    }
    

    /**
     * 登出
     *
     * @return
     */

    @PostMapping("logout")
    public Result<String> logout() {
        this.sysAuthService.logout();

        return Result.ok();
    }
}
