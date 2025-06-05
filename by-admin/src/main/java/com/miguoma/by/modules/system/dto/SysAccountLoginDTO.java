package com.miguoma.by.modules.system.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 账号登录
 *
 * @author lliyuu520 lliyuu520@gmail.com
 */
@Data
public class SysAccountLoginDTO implements Serializable {

    
    /**用户名 */
    private String username;

    /** 密码 */
    private String password;

}
