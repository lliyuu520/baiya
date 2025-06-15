package com.miguoma.by.modules.system.entity;

import com.miguoma.by.common.base.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author:
 * @date: 2025-06-14 10:00:00
 * @description: APK文件实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysApk extends BaseEntity {

    /**
     * apk文件地址
     */
    private String apkUrl;
    /**
     * 版本号
     */
    private Long versionCode;

    /**
     * 版本名称
     */
    private String versionName;

    /**
     * 版本描述
     */
    private String versionDesc;

}
