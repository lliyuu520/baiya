package com.miguoma.by.modules.client.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.StrUtil;
import com.miguoma.by.common.annotation.SysLogCut;
import com.miguoma.by.common.enums.SysLogModuleEnums;
import com.miguoma.by.common.enums.SysLogTypeEnums;
import com.miguoma.by.common.utils.ClientContextHolder;
import com.miguoma.by.common.utils.Result;
import com.miguoma.by.modules.client.dto.*;
import com.miguoma.by.modules.client.vo.PullCodeVO;
import com.miguoma.by.modules.equipment.dto.EquipmentClientDTO;
import com.miguoma.by.modules.equipment.dto.EquipmentMonitorDTO;
import com.miguoma.by.modules.equipment.entity.EquipmentClient;
import com.miguoma.by.modules.equipment.service.EquipmentClientService;
import com.miguoma.by.modules.equipment.service.EquipmentMonitorService;
import com.miguoma.by.modules.production.query.ProductionOrderQuery;
import com.miguoma.by.modules.production.service.ProductionDepartAndWorkshopService;
import com.miguoma.by.modules.production.service.ProductionFactoryService;
import com.miguoma.by.modules.production.service.ProductionOrderService;
import com.miguoma.by.modules.production.vo.ProductionOrderVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * 采集软件控制器
 *
 * @author liliangyu
 */
@Slf4j
@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
@SaIgnore
public class ClientController {

    private final ProductionFactoryService productionFactoryService;

    private final ProductionOrderService orderService;

    private final ProductionDepartAndWorkshopService productionDepartAndWorkshopService;

    private final EquipmentClientService equipmentClientService;
    private final EquipmentMonitorService equipmentMonitorService;

    /**
     * 登录
     * header 中携带 Token
     *
     * @param dto
     * @return
     */
    @PostMapping("/login")
    @SysLogCut(module = SysLogModuleEnums.CLIENT, type = SysLogTypeEnums.CLIENT_BIND)
    public Result<Long> login(@RequestBody MachineLoginDTO dto) {
        final String productionFactoryCode = dto.getProductionFactoryCode();
        final String productionWorkshopCode = dto.getProductionWorkshopCode();
        final Boolean checkFactoryCode = productionFactoryService.checkFactoryCode(productionFactoryCode);
        if (!checkFactoryCode) {
            log.error("工厂编码不存在:{}", productionWorkshopCode);
            return Result.error("工厂编码不存在");
        }

        final Boolean checkWorkshopCode = productionDepartAndWorkshopService.checkWorkshopName(productionWorkshopCode);
        if (!checkWorkshopCode) {
            log.error("产线不存在:{}", productionWorkshopCode);
            return Result.error("产线不存在");
        }

        final List<String> deparNameList = productionDepartAndWorkshopService.getDepartNameListByWorkshopName(productionWorkshopCode);
        if (CollUtil.isEmpty(deparNameList)) {
            log.error("车间不存在:{}", productionWorkshopCode);
            return Result.error("车间不存在");
        }
        if (CollUtil.size(deparNameList) != 1) {
            log.error("车间配置异常:{}", productionWorkshopCode);
            return Result.error("车间配置异常");
        }
        final String departName = deparNameList.get(0);

        final String macAddress = dto.getMacAddress();
        final String ip = dto.getIp();
        final String machineNo = dto.getMachineNo();
        final EquipmentClientDTO equipmentClientDTO = new EquipmentClientDTO();
        equipmentClientDTO.setFactoryNo(productionFactoryCode);
        equipmentClientDTO.setDepartNo(departName);
        equipmentClientDTO.setWorkshopNo(productionWorkshopCode);
        equipmentClientDTO.setMacAddress(macAddress);
        equipmentClientDTO.setMachineNo(machineNo);
        equipmentClientDTO.setIp(ip);
        final Long clientId = equipmentClientService.saveOne(equipmentClientDTO);

        return Result.ok(clientId);
    }

    /**
     * 采集软件验证密码
     *
     * @param validatePasswordDTO
     * @return
     */
    @PostMapping("/verifyPassword")
    @SysLogCut(module = SysLogModuleEnums.CLIENT, type = SysLogTypeEnums.VERIFY_PASSWORD)
    public Result<Boolean> verifyPassword(@RequestBody VerifyPasswordDTO validatePasswordDTO) {
        String password = validatePasswordDTO.getPassword();
        if (StrUtil.isBlank(password)) {
            return Result.error("密码不能为空!");


        }
        final EquipmentClient equipmentClient = ClientContextHolder.getEquipmentClient();
        MachineVerifyPasswordDTO machineVerifyPasswordDTO = new MachineVerifyPasswordDTO();
        machineVerifyPasswordDTO.setMacAddress(equipmentClient.getMacAddress());
        machineVerifyPasswordDTO.setPassword(password);
        final Boolean value = equipmentClientService.validatePassword(machineVerifyPasswordDTO);

        return Result.ok(value);
    }

    /**
     * 获取订单列表
     *
     * @param query
     */
    @GetMapping("/productionOrderList")
    @SysLogCut(module = SysLogModuleEnums.CLIENT, type = SysLogTypeEnums.ORDER_LIST)
    public Result<List<ProductionOrderVO>> getProductionOrderList(ProductionOrderQuery query) {
        final EquipmentClient equipmentClient = ClientContextHolder.getEquipmentClient();
        final String departNo = equipmentClient.getDepartNo();
        query.setProductionDepartName(departNo);
        query.setReworkFlag(true);
        final LocalDate orderDateEnd = LocalDateTimeUtil.now().toLocalDate();
        final LocalDate orderDateBegin = orderDateEnd.plusDays(-15);
        query.setOrderDateBegin(orderDateBegin);
        query.setOrderDateEnd(orderDateEnd);
        List<ProductionOrderVO> list = orderService.listVO(query);
        return Result.ok(list);
    }

    /**
     * 拉码
     */
    @PostMapping("/pullCode")
    @SysLogCut(module = SysLogModuleEnums.CLIENT, type = SysLogTypeEnums.PULL_CODE)
    public Result<PullCodeVO> pullCode(@RequestBody PullCodeDTO pullCodeDTO) {

        final PullCodeVO pullCodeVO = orderService.pullCode(pullCodeDTO);
        return Result.ok(pullCodeVO);

    }

    /**
     * 采集上传
     *
     * @param recordCodeUploadDTO 采集上传DTO
     * @return 采集上传结果
     */
    @PostMapping("/collectUpload")
    @SysLogCut(module = SysLogModuleEnums.CLIENT, type = SysLogTypeEnums.UPLOAD_CODE)
    public Result<String> collectUpload(@RequestBody RecordCodeUploadDTO recordCodeUploadDTO) {

        orderService.collectUpload(recordCodeUploadDTO);
        return Result.ok("采集上传成功");
    }

    /**
     * 上传监控信息
     */
    @PostMapping("/uploadMonitor")
    @SysLogCut(module = SysLogModuleEnums.CLIENT, type = SysLogTypeEnums.UPLOAD_MONITOR)
    public Result<String> uploadMonitor(@RequestBody EquipmentMonitorDTO equipmentMonitorDTO) {

        equipmentMonitorService.saveOne(equipmentMonitorDTO);
        return Result.ok("上传成功");

    }

}
