package com.miguoma.by.modules.client.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.core.codec.Base62;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.json.JSONUtil;
import com.miguoma.by.common.annotation.SysLogCut;
import com.miguoma.by.common.enums.SysLogModuleEnums;
import com.miguoma.by.common.enums.SysLogTypeEnums;
import com.miguoma.by.common.utils.ClientContextHolder;
import com.miguoma.by.common.utils.Result;
import com.miguoma.by.modules.client.dto.MachineLoginDTO;
import com.miguoma.by.modules.client.dto.PullCodeDTO;
import com.miguoma.by.modules.client.dto.RecordCodeUploadDTO;
import com.miguoma.by.modules.client.vo.PullCodeVO;
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


    /**
     * 登录
     * header 中携带 Token
     *
     * @param dto
     * @return
     */
    @PostMapping("/login")
    @SysLogCut(module = SysLogModuleEnums.CLIENT, type = SysLogTypeEnums.LOGIN)
    public Result<String> login(@RequestBody MachineLoginDTO dto) {
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
        final String jsonStr = JSONUtil.toJsonStr(dto);
        final String encode = Base62.encode(jsonStr);
        return Result.ok(encode);
    }


    /**
     * 获取订单列表
     *
     * @param query
     */
    @GetMapping("/productionOrderList")
    @SysLogCut(module = SysLogModuleEnums.CLIENT, type = SysLogTypeEnums.ORDER_LIST)
    public Result<List<ProductionOrderVO>> getProductionOrderList(ProductionOrderQuery query) {
        final MachineLoginDTO machineLoginDTO = ClientContextHolder.getMachineLoginDTO();
        final String productionWorkshopCode = machineLoginDTO.getProductionWorkshopCode();
        final List<String> departCodeList = productionDepartAndWorkshopService.getDepartCodeListByWorkshopName(productionWorkshopCode);
        query.setProductionDepartCodeList(departCodeList);
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


}
