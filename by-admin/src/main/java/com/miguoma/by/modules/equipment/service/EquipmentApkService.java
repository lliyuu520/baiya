package com.miguoma.by.modules.equipment.service;

import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.base.service.BaseService;
import com.miguoma.by.modules.equipment.dto.EquipmentApkDTO;
import com.miguoma.by.modules.equipment.entity.EquipmentApk;
import com.miguoma.by.modules.equipment.query.EquipmentApkQuery;
import com.miguoma.by.modules.equipment.vo.EquipmentApkVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * APK管理服务接口
 * 提供APK版本管理的核心业务逻辑，包括版本发布、查询、更新等功能
 *
 * @author liliangyu
 */
public interface EquipmentApkService extends BaseService<EquipmentApk> {

    /**
     * 分页查询APK版本列表
     * 支持按版本号、版本名称等条件进行查询
     *
     * @param query 查询条件，包含版本号、版本名称等
     * @return 分页结果，包含APK版本信息列表
     */
    PageVO<EquipmentApkVO> pageVO(EquipmentApkQuery query);

    /**
     * 新增APK版本
     * 用于发布新的APK版本
     *
     * @param dto APK版本信息，包含版本号、下载地址、版本描述等
     */
    void saveOne(EquipmentApkDTO dto);

    /**
     * 编辑APK版本
     * 用于修改已发布的APK版本信息
     *
     * @param dto APK版本信息，包含版本号、下载地址、版本描述等
     */
    void updateOne(EquipmentApkDTO dto);

    /**
     * 获取APK版本详情
     * 用于查看指定APK版本的详细信息
     *
     * @param id APK版本ID
     * @return APK版本详细信息
     */
    EquipmentApkVO getOneById(Long id);

    /**
     * 删除APK版本
     * 用于删除指定的APK版本记录
     *
     * @param id APK版本ID
     */
    void deleteById(Long id);

    /**
     * 查询最新的APK版本号
     * 
     * @return
     */
    Long getLatestVersionNo();

    /**
     * 获取最新APK版本
     * 用于获取当前最新的APK版本信息
     *
     * @return 最新APK版本信息
     */
    EquipmentApkVO getLatest();
/**
     * 上传APK文件
     * 用于上传新的APK文件
     *
     * @param file APK文件
     * @return 上传结果信息
     */
    String uploadApk(MultipartFile file);
}