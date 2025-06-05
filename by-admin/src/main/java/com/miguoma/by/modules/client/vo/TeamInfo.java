package com.miguoma.by.modules.client.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 班组信息视图对象 用于展示班组的基本信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamInfo implements Serializable {

    /**
     * 班组编码
     */
    private String code;

    /**
     * 班组名称
     */
    private String name;

 

}
