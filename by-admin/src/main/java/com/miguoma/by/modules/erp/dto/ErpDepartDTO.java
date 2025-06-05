package com.miguoma.by.modules.erp.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 生产部门 数据传输对象
 */
@Data
public class ErpDepartDTO implements Serializable {


    /**
     * 生产部门 编码
     */
    private String code;

    /**
     * 生产部门 名称
     */
    private String name;
    /**
     * 父级部门
     */
    private String parentCode;


}