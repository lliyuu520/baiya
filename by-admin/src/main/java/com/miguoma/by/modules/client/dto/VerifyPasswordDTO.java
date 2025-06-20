package com.miguoma.by.modules.client.dto;

import java.io.Serializable;

import lombok.Data;

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
