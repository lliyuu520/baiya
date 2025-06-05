package com.miguoma.by.modules.system.dto;

import com.miguoma.by.modules.system.entity.SysTableField;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 字段配置DTO
 *
 * @author liliangyu
 */
@Data
public class SysTableFieldDTO implements Serializable {
    /**
     * 表配置ID
     */
    private Long tableConfigId;
    /**
     * 字段配置集合
     */
    private List<SysTableField> sysTableFieldList;
}
