package com.miguoma.by.modules.production.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.miguoma.by.common.annotation.SysLogCut;
import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.enums.SysLogModuleEnums;
import com.miguoma.by.common.enums.SysLogTypeEnums;
import com.miguoma.by.common.utils.Result;
import com.miguoma.by.modules.production.dto.ProductionDepartAndWorkshopDTO;
import com.miguoma.by.modules.production.entity.ProductionDepartAndWorkshop;
import com.miguoma.by.modules.production.query.ProductionDepartAndWorkshopQuery;
import com.miguoma.by.modules.production.service.ProductionDepartAndWorkshopService;
import com.miguoma.by.modules.production.vo.ProductionDepartAndWorkshopVO;
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
@RequestMapping("/production/departAndWorkshop")
public class ProductionDepartAndWorkshopController {

    private final ProductionDepartAndWorkshopService productionDepartAndWorkshopService;

    /**
     * 分页查询工厂列表
     *
     * @param productionDepartAndWorkshopQuery 查询条件
     * @return 分页结果
     */
    @GetMapping("/page")
    @SysLogCut(type = SysLogTypeEnums.SELECT, module = SysLogModuleEnums.FACTORY)
    @SaCheckPermission(value = "production:departAndWorkshop:page")
    public Result<PageVO<ProductionDepartAndWorkshopVO>> page(ProductionDepartAndWorkshopQuery productionDepartAndWorkshopQuery) {
        PageVO<ProductionDepartAndWorkshopVO> pageResult = productionDepartAndWorkshopService.pageVO(productionDepartAndWorkshopQuery);
        return Result.ok(pageResult);
    }

    /**
     * 新增工厂
     *
     * @param departDTO 工厂信息
     * @return 操作结果
     */
    @PostMapping
    @SysLogCut(type = SysLogTypeEnums.INSERT, module = SysLogModuleEnums.FACTORY)
    @SaCheckPermission(value = "production:departAndWorkshop:save")
    public Result<String> save(@RequestBody ProductionDepartAndWorkshopDTO departDTO) {
        productionDepartAndWorkshopService.saveOne(departDTO);
        return Result.ok();
    }

    /**
     * 编辑工厂
     *
     * @param departDTO 工厂信息
     * @return 操作结果
     */
    @PutMapping
    @SysLogCut(type = SysLogTypeEnums.UPDATE, module = SysLogModuleEnums.FACTORY)
    @SaCheckPermission(value = "production:departAndWorkshop:update")
    public Result<String> update(@RequestBody ProductionDepartAndWorkshopDTO departDTO) {
        productionDepartAndWorkshopService.updateOne(departDTO);
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
    @SaCheckPermission(value = "production:departAndWorkshop:delete")
    public Result<String> delete(Long id) {
        productionDepartAndWorkshopService.deleteById(id);
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
    @SaCheckPermission(value = "production:departAndWorkshop:info")
    public Result<ProductionDepartAndWorkshopVO> info(Long id) {
        ProductionDepartAndWorkshopVO departVO = productionDepartAndWorkshopService.getOneById(id);
        return Result.ok(departVO);
    }

    /**
     * 获取工厂列表
     *
     * @return 工厂列表
     */
    @GetMapping("/list")
    public Result<List<ProductionDepartAndWorkshop>> list() {
        List<ProductionDepartAndWorkshop> productionDepartAndWorkshopList = productionDepartAndWorkshopService.list();
        return Result.ok(productionDepartAndWorkshopList);
    }
}
