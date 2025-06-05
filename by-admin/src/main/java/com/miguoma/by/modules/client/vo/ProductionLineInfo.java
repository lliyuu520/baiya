package com.miguoma.by.modules.client.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 产线信息视图对象 用于展示产线的基本信息及其下属设备信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionLineInfo implements Serializable {

    /**
     * 产线编码
     */
    private String code;

    /**
     * 产线名称
     */
    private String name;



    /**
     * 设备信息列表
     */
    private List<MachineInfo> machineInfoList = new ArrayList<>();
}
