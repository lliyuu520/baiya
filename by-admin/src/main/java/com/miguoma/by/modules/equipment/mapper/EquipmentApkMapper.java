package com.miguoma.by.modules.equipment.mapper;

import org.springframework.stereotype.Repository;

import com.miguoma.by.common.base.mapper.IBaseMapper;
import com.miguoma.by.modules.equipment.entity.SysApk;
import com.miguoma.by.modules.equipment.vo.SysApkVO;

/**
 * APK管理持久层接口
 * 提供APK版本管理的数据库操作，包括版本信息的增删改查等功能
 *
 * @author liliangyu
 */
@Repository
public interface SysApkMapper extends IBaseMapper<SysApk> {
    /**
     * 查询最新版本号
     * 
     * @return
     */
    Long getLatestVersionNo();

    /**
     * 获取最新APK版本
     * 查询当前最新的APK版本信息
     *
     * @return 最新APK版本信息
     */
    SysApkVO getLatest();
}