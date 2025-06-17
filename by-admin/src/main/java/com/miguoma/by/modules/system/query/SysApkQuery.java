package com.miguoma.by.modules.system.query;

import com.miguoma.by.common.base.query.BaseQuery;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * APK查询对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysApkQuery extends BaseQuery {

    /**
     * 版本号
     */
    private Long versionNo;

    /**
     * 版本名称
     */
    private String versionName;


}