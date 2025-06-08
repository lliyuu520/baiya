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
 * 生产部门&车间视图控制器
 * 提供生产部门&车间相关的增删改查接口
 *
 * @author liliangyu
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/production/departAndWorkshop")
public class ProductionDepartAndWorkshopController {

    private final ProductionDepartAndWorkshopService productionDepartAndWorkshopService;

    /**
     * 分页查询生产部门&车间列表
     *
     * @param productionDepartAndWorkshopQuery 查询条件
     * @return 分页结果
     */
    @GetMapping("/page")
    @SysLogCut(type = SysLogTypeEnums.SELECT, module = SysLogModuleEnums.FACTORY)
    @SaCheckPermission(value = "production:departAndWorkshop:page")
    public Result<PageVO<ProductionDepartAndWorkshopVO>> page(
            ProductionDepartAndWorkshopQuery productionDepartAndWorkshopQuery) {
        PageVO<ProductionDepartAndWorkshopVO> pageResult = productionDepartAndWorkshopService
                .pageVO(productionDepartAndWorkshopQuery);
        return Result.ok(pageResult);
    }
    /**
     * 获取生产部门&车间详情
     * @param id
     * @return
     */
    @GetMapping("/info")
    public Result<ProductionDepartAndWorkshop> info( Long id) {
        ProductionDepartAndWorkshop productionDepartAndWorkshop= productionDepartAndWorkshopService.getById(id);
        return Result.ok(productionDepartAndWorkshop);
    }

   
    /**
     * 获取生产部门&车间列表
     *
     * @return 生产部门&车间列表
     */
    @GetMapping("/list")
    public Result<List<ProductionDepartAndWorkshop>> list() {
        List<ProductionDepartAndWorkshop> productionDepartAndWorkshopList = productionDepartAndWorkshopService.list();
        return Result.ok(productionDepartAndWorkshopList);
    }


    /**配置别名 */
    @PostMapping("/configAlias")
    public Result<Void> configAlias(@RequestBody ProductionDepartAndWorkshopDTO productionDepartAndWorkshopDTO) {
        productionDepartAndWorkshopService.configAlias(productionDepartAndWorkshopDTO);
        return Result.ok();
    }

    /**
     * 配置编码规则
     * @param productionDepartAndWorkshopDTO
     * @return
     */
    @PostMapping("/configCodeRule")
    public Result<Void> configCodeRule(@RequestBody ProductionDepartAndWorkshopDTO productionDepartAndWorkshopDTO) {
        productionDepartAndWorkshopService.configCodeRule(productionDepartAndWorkshopDTO);
        return Result.ok();
    }
}
