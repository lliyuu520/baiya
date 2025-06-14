package com.miguoma.by.modules.client.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * pda登录参数
 * @author liliangyu
 */
@Data
public class PdaLoginDTO implements Serializable {

    /** 用户名 */
    private String username;


    /** 密码 */
    private String password;



}
