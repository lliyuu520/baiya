package com.miguoma.by.modules.client.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.miguoma.by.common.annotation.SysLogCut;
import com.miguoma.by.common.enums.SysLogModuleEnums;
import com.miguoma.by.common.enums.SysLogTypeEnums;
import com.miguoma.by.common.utils.Result;
import com.miguoma.by.common.utils.SysUserUtil;
import com.miguoma.by.modules.client.dto.PullCodeDTO;
import com.miguoma.by.modules.client.dto.RecordCodeUploadDTO;
import com.miguoma.by.modules.client.vo.PullCodeVO;
import com.miguoma.by.modules.record.service.*;
import com.miguoma.by.modules.client.dto.TeamLoginDTO;
import com.miguoma.by.modules.production.query.ProductionOrderQuery;
import com.miguoma.by.modules.production.service.*;
import com.miguoma.by.modules.production.vo.ProductionOrderVO;
import com.miguoma.by.modules.system.service.SysAuthService;
import com.miguoma.by.modules.system.service.SysCodeRuleService;
import com.miguoma.by.modules.system.vo.SysTokenVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * @author liliangyu
 */
@Slf4j
@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {

    private final ProductionFactoryService productionFactoryService;

    private final ProductionTeamService productionTeamService;

    private final SysAuthService sysAuthService;
    private final ProductionOrderService orderService;
    private final ProductionProductService productionProductService;
    private final SysCodeRuleService sysCodeRuleService;

    private final RecordBoxCodeService recordBoxCodeService;
    private final ProductionShiftService productionShiftService;

   

    /**
     * 登录 客户端登录不需要密码
     * header 中携带 Authorization
     *
     * @param dto
     * @return
     */
    @PostMapping("login")
    @SaIgnore
    public Result<String> login(@RequestBody TeamLoginDTO dto) {
        final SysTokenVO sysTokenVO = sysAuthService.loginByClient(dto);
        return Result.ok(sysTokenVO.getAccessToken());
    }

    /**
     * 退出登录
     */
    @GetMapping("logout")
    @SaIgnore
    public Result<String> logout() {
        sysAuthService.logout();
        return Result.ok("退出成功");
    }

    /**
     * 获取订单列表
     *
     * @param query
     */
//    @SaCheckLogin
    @GetMapping("productionOrderList")
    @SysLogCut(module = SysLogModuleEnums.CLIENT, type = SysLogTypeEnums.SELECT)
    public Result<List<ProductionOrderVO>> getProductionOrderList(ProductionOrderQuery query) {
        // 登录账号即为所属车间
        final String workshopCode = SysUserUtil.getUserInfo().getUsername();
        query.setReworkFlag(true);
        final LocalDate orderDateEnd = LocalDateTimeUtil.now().toLocalDate();
        final LocalDate orderDateBegin = orderDateEnd.plusDays(-15);
        query.setOrderDateBegin(orderDateBegin);
        query.setOrderDateEnd(orderDateEnd);
        List< ProductionOrderVO> list = orderService.listVO(query);
        return Result.ok(list);
    }
        
        
        

    /**
     * 拉码
     */
    @PostMapping("pullCode")
//    @SaCheckLogin
    @SysLogCut(module = SysLogModuleEnums.CLIENT, type = SysLogTypeEnums.INSERT)
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
//    @SaCheckLogin
    public Result<String> collectUpload(@RequestBody RecordCodeUploadDTO recordCodeUploadDTO) {
        orderService.collectUpload(recordCodeUploadDTO);
        return Result.ok("采集上传成功");
    }


}
