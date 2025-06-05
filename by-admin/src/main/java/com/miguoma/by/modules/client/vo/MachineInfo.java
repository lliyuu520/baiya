package com.miguoma.by.modules.client.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 设备信息视图对象 用于展示设备的基本信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MachineInfo implements Serializable {

    /**
     * 设备编码
     */
    private String code;

    /**
     * 设备名称
     */
    private String name;

  
}
