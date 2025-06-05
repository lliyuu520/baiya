package com.miguoma.by.modules.system.vo;

import com.miguoma.by.modules.system.entity.SysTableConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysTableConfigVO extends SysTableConfig {
    /**
     * 表字段列表
     */
    private List<SysTableFieldVO> tableFieldVOList = new ArrayList<>();

}
