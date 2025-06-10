package com.miguoma.by.modules.production.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.miguoma.by.common.annotation.SysLogCut;
import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.enums.SysLogModuleEnums;
import com.miguoma.by.common.enums.SysLogTypeEnums;
import com.miguoma.by.common.utils.Result;
import com.miguoma.by.modules.production.entity.ProductionShift;
import com.miguoma.by.modules.production.query.ProductionShiftQuery;
import com.miguoma.by.modules.production.service.ProductionShiftService;
import com.miguoma.by.modules.production.vo.ProductionShiftVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 班次视图控制器
 * 提供班次相关的增删改查接口
 *
 * @author liliangyu
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/production/shift")
public class ProductionShiftController {

    private final ProductionShiftService productionShiftService;

    /**
     * 分页查询班次列表
     *
     * @param productionShiftQuery 查询条件
     * @return 分页结果
     */
    @GetMapping("/page")
    @SysLogCut(type = SysLogTypeEnums.SELECT, module = SysLogModuleEnums.PRODUCTION_LINE)
    @SaCheckPermission(value = "production:shift:page")
    public Result<PageVO<ProductionShiftVO>> page(ProductionShiftQuery productionShiftQuery) {
        PageVO<ProductionShiftVO> pageResult = productionShiftService.pageVO(productionShiftQuery);
        return Result.ok(pageResult);
    }


    /**
     * 获取班次列表
     *
     * @return 班次列表
     */
    @GetMapping("/list")
    public Result<List<ProductionShift>> list() {
        List<ProductionShift> productionShiftList = productionShiftService.list();
        return Result.ok(productionShiftList);
    }


}
