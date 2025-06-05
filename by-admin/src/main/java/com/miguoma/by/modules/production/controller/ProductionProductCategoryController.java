package com.miguoma.by.modules.production.controller;

import java.util.List;

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
import com.miguoma.by.modules.production.dto.ProductionProductCategoryDTO;
import com.miguoma.by.modules.production.entity.ProductionProductCategory;
import com.miguoma.by.modules.production.query.ProductionProductCategoryQuery;
import com.miguoma.by.modules.production.service.ProductionProductCategoryService;
import com.miguoma.by.modules.production.vo.ProductionProductCategoryVO;

import cn.dev33.satoken.annotation.SaCheckPermission;
import lombok.RequiredArgsConstructor;

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
     * 新增产品分类
     *
     * @param productCategoryDTO 产品分类信息
     * @return 操作结果
     */
    @PostMapping
    @SysLogCut(type = SysLogTypeEnums.INSERT, module = SysLogModuleEnums.PRODUCT_CATEGORY)
    @SaCheckPermission(value = "production:productCategory:save")
    public Result<String> save(@RequestBody ProductionProductCategoryDTO productCategoryDTO) {
        productionProductCategoryService.saveOne(productCategoryDTO);
        return Result.ok();
    }

    /**
     * 编辑产品分类
     *
     * @param productCategoryDTO 产品分类信息
     * @return 操作结果
     */
    @PutMapping
    @SysLogCut(type = SysLogTypeEnums.UPDATE, module = SysLogModuleEnums.PRODUCT_CATEGORY)
    @SaCheckPermission(value = "production:productCategory:update")
    public Result<String> update(@RequestBody ProductionProductCategoryDTO productCategoryDTO) {
        productionProductCategoryService.updateOne(productCategoryDTO);
        return Result.ok();
    }

    /**
     * 删除产品分类
     *
     * @param id 产品分类ID
     * @return 操作结果
     */
    @DeleteMapping("/delete")
    @SysLogCut(type = SysLogTypeEnums.DELETE, module = SysLogModuleEnums.PRODUCT_CATEGORY)
    @SaCheckPermission(value = "production:productCategory:delete")
    public Result<String> delete(Long id) {
        productionProductCategoryService.deleteById(id);
        return Result.ok();
    }

    /**
     * 获取产品分类详情
     *
     * @param id 产品分类ID
     * @return 产品分类详情
     */
    @GetMapping("/info")
    @SysLogCut(type = SysLogTypeEnums.VIEW, module = SysLogModuleEnums.PRODUCT_CATEGORY)
    @SaCheckPermission(value = "production:productCategory:info")
    public Result<ProductionProductCategoryVO> info(Long id) {
        ProductionProductCategoryVO productCategoryVO = productionProductCategoryService.getOneById(id);
        return Result.ok(productCategoryVO);
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