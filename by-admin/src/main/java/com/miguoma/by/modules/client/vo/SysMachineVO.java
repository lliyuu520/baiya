package com.miguoma.by.modules.client.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 设备
 *
 * @author liliangyu
 */
@Data
public class SysMachineVO  implements Serializable {
    /**
     * token
     */
    private String token;
    /**
     * 机器密码
     */
    private String machinePassword;
}
