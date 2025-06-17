package com.miguoma.by.modules.equipment.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.miguoma.by.common.annotation.SysLogCut;
import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.enums.SysLogModuleEnums;
import com.miguoma.by.common.enums.SysLogTypeEnums;
import com.miguoma.by.common.utils.Result;
import com.miguoma.by.modules.equipment.dto.EquipmentClientDTO;
import com.miguoma.by.modules.equipment.query.EquipmentClientQuery;
import com.miguoma.by.modules.equipment.service.EquipmentClientService;
import com.miguoma.by.modules.equipment.vo.EquipmentClientVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Client管理控制器
 * 提供Client版本管理的相关接口，包括版本发布、查询、更新等功能
 *
 * @author liliangyu
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/equipment/client")
public class EquipmentClientController {

    private final EquipmentClientService equipmentClientService;

    /**
     * 分页查询Client版本列表
     * 支持按版本号、版本名称等条件进行查询
     *
     * @param equipmentClientQuery 查询条件，包含版本号、版本名称等
     * @return 分页结果，包含Client版本信息列表
     */
    @GetMapping("/page")
    @SysLogCut(type = SysLogTypeEnums.PAGE, module = SysLogModuleEnums.CLIENT)
    @SaCheckPermission(value = "equipment:client:page")
    public Result<PageVO<EquipmentClientVO>> page(EquipmentClientQuery equipmentClientQuery) {
        PageVO<EquipmentClientVO> pageResult = equipmentClientService.pageVO(equipmentClientQuery);
        return Result.ok(pageResult);
    }

    /**
     * 修改密码
     * @param equipmentClientDTO
     * @return
     */
    @PostMapping
    @SysLogCut(type = SysLogTypeEnums.UPDATE_PASSWORD, module = SysLogModuleEnums.CLIENT)
    @SaCheckPermission(value = "equipment:client:updatePassword")
    public Result<Boolean> updatePassword(@RequestBody EquipmentClientDTO equipmentClientDTO) {
        equipmentClientService.updatePassword(equipmentClientDTO);
        return Result.ok();
    }



    



}