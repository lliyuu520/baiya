package com.miguoma.by.modules.equipment.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miguoma.by.common.annotation.SysLogCut;
import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.enums.SysLogModuleEnums;
import com.miguoma.by.common.enums.SysLogTypeEnums;
import com.miguoma.by.common.utils.Result;
import com.miguoma.by.modules.equipment.entity.EquipmentMonitor;
import com.miguoma.by.modules.equipment.query.EquipmentMonitorQuery;
import com.miguoma.by.modules.equipment.service.EquipmentMonitorService;

import cn.dev33.satoken.annotation.SaCheckPermission;
import lombok.RequiredArgsConstructor;

/**
 * 设备监控控制器
 * 提供设备监控的分页、详情、增删改等接口
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/equipment/monitor")
public class EquipmentMonitorController {

    private final EquipmentMonitorService equipmentMonitorService;

    /**
     * 分页查询设备监控列表
     */
    @GetMapping("/page")
    @SysLogCut(type = SysLogTypeEnums.PAGE, module = SysLogModuleEnums.MONITOR)
    @SaCheckPermission(value = "equipment:monitor:page")
    public Result<PageVO<EquipmentMonitor>> page(EquipmentMonitorQuery query) {
        return Result.ok(equipmentMonitorService.pageVO(query));
    }

}