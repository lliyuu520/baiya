package com.miguoma.by.modules.client.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 验证密码dto
 */
@Data
public class VerifyPasswordDTO implements Serializable {
    /**
     * 密码
     */
    private String password;

}
