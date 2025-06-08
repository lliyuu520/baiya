package com.miguoma.by.modules.production.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.miguoma.by.common.annotation.SysLogCut;
import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.enums.SysLogModuleEnums;
import com.miguoma.by.common.enums.SysLogTypeEnums;
import com.miguoma.by.common.utils.Result;
import com.miguoma.by.modules.production.dto.ProductionFactoryDTO;
import com.miguoma.by.modules.production.entity.ProductionFactory;
import com.miguoma.by.modules.production.query.ProductionFactoryQuery;
import com.miguoma.by.modules.production.service.ProductionFactoryService;
import com.miguoma.by.modules.production.vo.ProductionFactoryVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 工厂视图控制器
 * 提供工厂相关的增删改查接口
 *
 * @author liliangyu
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/production/factory")
public class ProductionFactoryController {

    private final ProductionFactoryService productionFactoryService;

    /**
     * 分页查询工厂列表
     *
     * @param productionFactoryQuery 查询条件
     * @return 分页结果
     */
    @GetMapping("/page")
    @SysLogCut(type = SysLogTypeEnums.SELECT, module = SysLogModuleEnums.FACTORY)
    @SaCheckPermission(value = "production:factory:page")
    public Result<PageVO<ProductionFactoryVO>> page(ProductionFactoryQuery productionFactoryQuery) {
        PageVO<ProductionFactoryVO> pageResult = productionFactoryService.pageVO(productionFactoryQuery);
        return Result.ok(pageResult);
    }

    /**
     * 新增工厂
     *
     * @param factoryDTO 工厂信息
     * @return 操作结果
     */
    @PostMapping
    @SysLogCut(type = SysLogTypeEnums.INSERT, module = SysLogModuleEnums.FACTORY)
    @SaCheckPermission(value = "production:factory:save")
    public Result<String> save(@RequestBody ProductionFactoryDTO factoryDTO) {
        productionFactoryService.saveOne(factoryDTO);
        return Result.ok();
    }

    /**
     * 编辑工厂
     *
     * @param factoryDTO 工厂信息
     * @return 操作结果
     */
    @PutMapping
    @SysLogCut(type = SysLogTypeEnums.UPDATE, module = SysLogModuleEnums.FACTORY)
    @SaCheckPermission(value = "production:factory:update")
    public Result<String> update(@RequestBody ProductionFactoryDTO factoryDTO) {
        productionFactoryService.updateOne(factoryDTO);
        return Result.ok();
    }

    /**
     * 删除工厂
     *
     * @param id 工厂ID
     * @return 操作结果
     */
    @DeleteMapping("/delete")
    @SysLogCut(type = SysLogTypeEnums.DELETE, module = SysLogModuleEnums.FACTORY)
    @SaCheckPermission(value = "production:factory:delete")
    public Result<String> delete(Long id) {
        productionFactoryService.deleteById(id);
        return Result.ok();
    }

    /**
     * 获取工厂详情
     *
     * @param id 工厂ID
     * @return 工厂详情
     */
    @GetMapping("/info")
    @SysLogCut(type = SysLogTypeEnums.VIEW, module = SysLogModuleEnums.FACTORY)
    @SaCheckPermission(value = "production:factory:info")
    public Result<ProductionFactoryVO> info(Long id) {
        ProductionFactoryVO factoryVO = productionFactoryService.getOneById(id);
        return Result.ok(factoryVO);
    }

    /**
     * 获取工厂列表
     *
     * @return 工厂列表
     */
    @GetMapping("/list")
    public Result<List<ProductionFactory>> list() {
        List<ProductionFactory> productionFactoryList = productionFactoryService.list();
        return Result.ok(productionFactoryList);
    }
}
