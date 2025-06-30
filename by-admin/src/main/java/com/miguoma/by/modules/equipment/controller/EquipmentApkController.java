package com.miguoma.by.modules.equipment.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.miguoma.by.common.annotation.SysLogCut;
import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.enums.SysLogModuleEnums;
import com.miguoma.by.common.enums.SysLogTypeEnums;
import com.miguoma.by.common.utils.Result;
import com.miguoma.by.modules.equipment.dto.EquipmentApkDTO;
import com.miguoma.by.modules.equipment.query.EquipmentApkQuery;
import com.miguoma.by.modules.equipment.service.EquipmentApkService;
import com.miguoma.by.modules.equipment.vo.EquipmentApkVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * APK管理控制器
 * 提供APK版本管理的相关接口，包括版本发布、查询、更新等功能
 *
 * @author liliangyu
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/equipment/apk")
public class EquipmentApkController {

    private final EquipmentApkService equipmentApkService;

    /**
     * 分页查询APK版本列表
     * 支持按版本号、版本名称等条件进行查询
     *
     * @param equipmentApkQuery 查询条件，包含版本号、版本名称等
     * @return 分页结果，包含APK版本信息列表
     */
    @GetMapping("/page")
    @SysLogCut(type = SysLogTypeEnums.PAGE, module = SysLogModuleEnums.APK)
    @SaCheckPermission(value = "equipment:apk:page")
    public Result<PageVO<EquipmentApkVO>> page(EquipmentApkQuery equipmentApkQuery) {
        PageVO<EquipmentApkVO> pageResult = equipmentApkService.pageVO(equipmentApkQuery);
        return Result.ok(pageResult);
    }

    /**
     * 新增APK版本
     * 用于发布新的APK版本
     *
     * @param apkDTO APK版本信息，包含版本号、下载地址、版本描述等
     * @return 操作结果
     */
    @PostMapping
    @SysLogCut(type = SysLogTypeEnums.INSERT, module = SysLogModuleEnums.APK)
    @SaCheckPermission(value = "equipment:apk:save")
    public Result<String> save(@RequestBody EquipmentApkDTO apkDTO) {
        equipmentApkService.saveOne(apkDTO);
        return Result.ok();
    }

    /**
     * 编辑APK版本
     * 用于修改已发布的APK版本信息
     *
     * @param apkDTO APK版本信息，包含版本号、下载地址、版本描述等
     * @return 操作结果
     */
    @PutMapping
    @SysLogCut(type = SysLogTypeEnums.UPDATE, module = SysLogModuleEnums.APK)
    @SaCheckPermission(value = "equipment:apk:update")
    public Result<String> update(@RequestBody EquipmentApkDTO apkDTO) {
        equipmentApkService.updateOne(apkDTO);
        return Result.ok();
    }

    /**
     * 删除APK版本
     * 用于删除指定的APK版本记录
     *
     * @param id APK版本ID
     * @return 操作结果
     */
    @DeleteMapping("/delete")
    @SysLogCut(type = SysLogTypeEnums.DELETE, module = SysLogModuleEnums.APK)
    @SaCheckPermission(value = "equipment:apk:delete")
    public Result<String> delete(Long id) {
        equipmentApkService.deleteById(id);
        return Result.ok();
    }

    /**
     * 获取APK版本详情
     * 用于查看指定APK版本的详细信息
     *
     * @param id APK版本ID
     * @return APK版本详细信息
     */
    @GetMapping("/info")
    @SysLogCut(type = SysLogTypeEnums.VIEW, module = SysLogModuleEnums.APK)
    @SaCheckPermission(value = "equipment:apk:info")
    public Result<EquipmentApkVO> info(Long id) {
        EquipmentApkVO apkVO = equipmentApkService.getOneById(id);
        return Result.ok(apkVO);
    }

    /**
     * 上传apk
     */
    @PostMapping("/uploadApk")
    public Result<String> uploadApk(MultipartFile file) {
        String uploadApk = equipmentApkService.uploadApk(file);
        return Result.ok(uploadApk);
    }
    



}