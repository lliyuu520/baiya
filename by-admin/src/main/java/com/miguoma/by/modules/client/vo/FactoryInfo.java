package com.miguoma.by.modules.client.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 工厂信息视图对象 用于展示工厂的基本信息及其下属车间信息
 *
 * @author liliangyu
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FactoryInfo implements Serializable {

    /**
     * 工厂编码
     */
    private String code;

    /**
     * 工厂名称
     */
    private String name;

   

    /**
     * 车间信息列表
     */
    private List<WorkShopInfo> workshopInfoList = new ArrayList<>();
}
