package com.miguoma.by.modules.production.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.miguoma.by.common.annotation.SysLogCut;
import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.enums.SysLogModuleEnums;
import com.miguoma.by.common.enums.SysLogTypeEnums;
import com.miguoma.by.common.utils.Result;
import com.miguoma.by.modules.production.dto.ProductionShiftDTO;
import com.miguoma.by.modules.production.entity.ProductionShift;
import com.miguoma.by.modules.production.query.ProductionShiftQuery;
import com.miguoma.by.modules.production.service.ProductionShiftService;
import com.miguoma.by.modules.production.vo.ProductionShiftVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 产线视图控制器
 * 提供产线相关的增删改查接口
 *
 * @author liliangyu
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/production/shift")
public class ProductionShiftController {

    private final ProductionShiftService productionShiftService;

    /**
     * 分页查询产线列表
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
     * 新增产线
     *
     * @param productionShiftDTO 产线信息
     * @return 操作结果
     */
    @PostMapping
    @SysLogCut(type = SysLogTypeEnums.INSERT, module = SysLogModuleEnums.PRODUCTION_LINE)
    @SaCheckPermission(value = "production:shift:save")
    public Result<String> save(@RequestBody ProductionShiftDTO productionShiftDTO) {
        productionShiftService.saveOne(productionShiftDTO);
        return Result.ok();
    }

    /**
     * 编辑产线
     *
     * @param productionShiftDTO 产线信息
     * @return 操作结果
     */
    @PutMapping
    @SysLogCut(type = SysLogTypeEnums.UPDATE, module = SysLogModuleEnums.PRODUCTION_LINE)
    @SaCheckPermission(value = "production:shift:update")
    public Result<String> update(@RequestBody ProductionShiftDTO productionShiftDTO) {
        productionShiftService.updateOne(productionShiftDTO);
        return Result.ok();
    }

    /**
     * 删除产线
     *
     * @param id 产线ID
     * @return 操作结果
     */
    @DeleteMapping("/delete")
    @SysLogCut(type = SysLogTypeEnums.DELETE, module = SysLogModuleEnums.PRODUCTION_LINE)
    @SaCheckPermission(value = "production:shift:delete")
    public Result<String> delete(Long id) {
        productionShiftService.deleteById(id);
        return Result.ok();
    }

    /**
     * 获取产线详情
     *
     * @param id 产线ID
     * @return 产线详情
     */
    @GetMapping("/info")
    @SysLogCut(type = SysLogTypeEnums.VIEW, module = SysLogModuleEnums.PRODUCTION_LINE)
    @SaCheckPermission(value = "production:shift:info")
    public Result<ProductionShiftVO> info(Long id) {
        ProductionShiftVO productionShiftVO = productionShiftService.getOneById(id);
        return Result.ok(productionShiftVO);
    }

    /**
     * 获取产线列表
     *
     * @return 产线列表
     */
    @GetMapping("/list")
    public Result<List<ProductionShift>> list() {
        List<ProductionShift> productionShiftList = productionShiftService.list();
        return Result.ok(productionShiftList);
    }


}
