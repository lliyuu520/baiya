package com.miguoma.by.modules.client.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 产线登录DTO
 * @author liliangyu
 */
@Data
public class MachineVerifyPasswordDTO implements Serializable {



    /**
     * mac地址
     */
    private String macAddress;

    /**
     * 密码
     */
    private String password;






}
