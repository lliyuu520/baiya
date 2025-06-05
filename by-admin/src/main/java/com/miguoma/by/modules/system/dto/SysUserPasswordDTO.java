package com.miguoma.by.modules.system.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户修改密码
 *
 * @author lliyuu520 lliyuu520@gmail.com
 */
@Data
public class SysUserPasswordDTO implements Serializable {


    private String password;

    private String newPassword;

}
