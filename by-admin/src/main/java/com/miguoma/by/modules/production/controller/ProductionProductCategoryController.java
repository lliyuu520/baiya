package com.miguoma.by.modules.production.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.miguoma.by.common.annotation.SysLogCut;
import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.enums.SysLogModuleEnums;
import com.miguoma.by.common.enums.SysLogTypeEnums;
import com.miguoma.by.common.utils.Result;
import com.miguoma.by.modules.production.entity.ProductionProductCategory;
import com.miguoma.by.modules.production.query.ProductionProductCategoryQuery;
import com.miguoma.by.modules.production.service.ProductionProductCategoryService;
import com.miguoma.by.modules.production.vo.ProductionProductCategoryVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 产品分类控制器
 * 提供产品分类相关的增删改查接口
 *
 * @author liliangyu
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/production/productCategory")
public class ProductionProductCategoryController {

    private final ProductionProductCategoryService productionProductCategoryService;

    /**
     * 分页查询产品分类列表
     *
     * @param productCategoryQuery 查询条件
     * @return 分页结果
     */
    @GetMapping("/page")
    @SysLogCut(type = SysLogTypeEnums.SELECT, module = SysLogModuleEnums.PRODUCT_CATEGORY)
    @SaCheckPermission(value = "production:productCategory:page")
    public Result<PageVO<ProductionProductCategoryVO>> page(ProductionProductCategoryQuery productCategoryQuery) {
        PageVO<ProductionProductCategoryVO> pageResult = productionProductCategoryService.pageVO(productCategoryQuery);
        return Result.ok(pageResult);
    }

   


    /**
     * 获取产品分类列表
     *
     * @return 产品分类列表
     */
    @GetMapping("/list")
    public Result<List<ProductionProductCategory>> list() {
        List<ProductionProductCategory> productCategoryList = productionProductCategoryService.list();
        return Result.ok(productCategoryList);
    }

    /**
     * 根据父级编码查询产品分类列表
     *
     * @param parentCode 父级编码
     * @return 产品分类列表
     */
    @GetMapping("/listByParentCode")
    public Result<List<ProductionProductCategory>> listByParentCode(String parentCode) {
        List<ProductionProductCategory> productCategoryList = productionProductCategoryService
                .listByParentCode(parentCode);
        return Result.ok(productCategoryList);
    }
}