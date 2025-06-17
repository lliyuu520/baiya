package com.miguoma.by.modules.system.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.miguoma.by.common.annotation.SysLogCut;
import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.enums.SysLogModuleEnums;
import com.miguoma.by.common.enums.SysLogTypeEnums;
import com.miguoma.by.common.utils.Result;
import com.miguoma.by.modules.system.dto.SysApkDTO;
import com.miguoma.by.modules.system.query.SysApkQuery;
import com.miguoma.by.modules.system.service.SysApkService;
import com.miguoma.by.modules.system.vo.SysApkVO;

import cn.dev33.satoken.annotation.SaCheckPermission;
import lombok.RequiredArgsConstructor;

/**
 * APK管理控制器
 * 提供APK版本管理的相关接口，包括版本发布、查询、更新等功能
 *
 * @author liliangyu
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/apk")
public class SysApkController {

    private final SysApkService sysApkService;

    /**
     * 分页查询APK版本列表
     * 支持按版本号、版本名称等条件进行查询
     *
     * @param sysApkQuery 查询条件，包含版本号、版本名称等
     * @return 分页结果，包含APK版本信息列表
     */
    @GetMapping("/page")
    @SysLogCut(type = SysLogTypeEnums.PAGE, module = SysLogModuleEnums.APK)
    @SaCheckPermission(value = "system:apk:page")
    public Result<PageVO<SysApkVO>> page(SysApkQuery sysApkQuery) {
        PageVO<SysApkVO> pageResult = sysApkService.pageVO(sysApkQuery);
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
    @SaCheckPermission(value = "system:apk:save")
    public Result<String> save(@RequestBody SysApkDTO apkDTO) {
        sysApkService.saveOne(apkDTO);
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
    @SaCheckPermission(value = "system:apk:update")
    public Result<String> update(@RequestBody SysApkDTO apkDTO) {
        sysApkService.updateOne(apkDTO);
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
    @SaCheckPermission(value = "system:apk:delete")
    public Result<String> delete(Long id) {
        sysApkService.deleteById(id);
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
    @SaCheckPermission(value = "system:apk:info")
    public Result<SysApkVO> info(Long id) {
        SysApkVO apkVO = sysApkService.getOneById(id);
        return Result.ok(apkVO);
    }

    /**
     * 上传apk
     */
    @PostMapping("/uploadApk")
    public Result<String> uploadApk(MultipartFile file) {
        sysApkService.uploadApk(file);
        return Result.ok();
    }
    



}