package com.miguoma.by.modules.client.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 车间信息视图对象 用于展示车间的基本信息及其下属产线信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkShopInfo implements Serializable {

    /**
     * 车间编码
     */
    private String code;

    /**
     * 车间名称
     */
    private String name;

 
    /**
     * 产线信息列表
     */
    private List<ProductionLineInfo> productionLineInfoList = new ArrayList<>();
}
