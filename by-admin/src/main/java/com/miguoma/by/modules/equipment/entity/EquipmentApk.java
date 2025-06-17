package com.miguoma.by.modules.equipment.entity;

import com.miguoma.by.common.base.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * APK管理实体类
 * 用于存储APK版本信息，包括版本号、下载地址等
 *
 * @author liliangyu
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class EquipmentApk extends BaseEntity {

    /**
     * APK文件下载地址
     * 存储APK文件的完整URL路径
     */
    private String apkUrl;

    /**
     * 版本号
     * 用于标识APK的版本，采用数字形式，如：1001
     */
    private Long versionNo;

    /**
     * 版本名称
     * 用于显示给用户看的版本名称，如：v1.0.1
     */
    private String versionName;

    /**
     * 版本描述
     * 描述当前版本的主要更新内容和特性
     */
    private String versionDesc;

}
