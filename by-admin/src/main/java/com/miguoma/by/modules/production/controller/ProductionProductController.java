package com.miguoma.by.modules.production.controller;

import java.util.List;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
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
import com.miguoma.by.modules.production.dto.ProductionProductDTO;
import com.miguoma.by.modules.production.entity.ProductionProduct;
import com.miguoma.by.modules.production.query.ProductionProductQuery;
import com.miguoma.by.modules.production.service.ProductionProductService;
import com.miguoma.by.modules.production.vo.ProductionProductVO;

import cn.dev33.satoken.annotation.SaCheckPermission;
import lombok.RequiredArgsConstructor;

/**
 * 产品视图控制器
 * 提供产品相关的增删改查接口
 *
 * @author liliangyu
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/production/product")
public class ProductionProductController {

    private final ProductionProductService productionProductService;

    /**
     * 分页查询产品列表
     *
     * @param productQuery 查询条件
     * @return 分页结果
     */
    @GetMapping("/page")
    @SysLogCut(type = SysLogTypeEnums.SELECT, module = SysLogModuleEnums.PRODUCT)
    @SaCheckPermission(value = "production:product:page")
    public Result<PageVO<ProductionProductVO>> page(ProductionProductQuery productQuery) {
        PageVO<ProductionProductVO> pageResult = productionProductService.pageVO(productQuery);
        return Result.ok(pageResult);
    }

    /**
     * 新增产品
     *
     * @param productDTO 产品信息
     * @return 操作结果
     */
    @PostMapping
    @SysLogCut(type = SysLogTypeEnums.INSERT, module = SysLogModuleEnums.PRODUCT)
    @SaCheckPermission(value = "production:product:save")
    public Result<String> save(@RequestBody ProductionProductDTO productDTO) {
        productionProductService.saveOne(productDTO);
        return Result.ok();
    }

    /**
     * 编辑产品
     *
     * @param productDTO 产品信息
     * @return 操作结果
     */
    @PutMapping
    @SysLogCut(type = SysLogTypeEnums.UPDATE, module = SysLogModuleEnums.PRODUCT)
    @SaCheckPermission(value = "production:product:update")
    public Result<String> update(@RequestBody ProductionProductDTO productDTO) {
        productionProductService.updateOne(productDTO);
        return Result.ok();
    }

    /**
     * 删除产品
     *
     * @param id 产品ID
     * @return 操作结果
     */
    @DeleteMapping("/delete")
    @SysLogCut(type = SysLogTypeEnums.DELETE, module = SysLogModuleEnums.PRODUCT)
    @SaCheckPermission(value = "production:product:delete")
    public Result<String> delete(Long id) {
        productionProductService.deleteById(id);
        return Result.ok();
    }

    /**
     * 获取产品详情
     *
     * @param id 产品ID
     * @return 产品详情
     */
    @GetMapping("/info")
    @SysLogCut(type = SysLogTypeEnums.VIEW, module = SysLogModuleEnums.PRODUCT)
    @SaCheckPermission(value = "production:product:info")
    public Result<ProductionProductVO> info(Long id) {
        ProductionProductVO productVO = productionProductService.getOneById(id);
        return Result.ok(productVO);
    }

    /**
     * 获取产品列表
     *
     * @return 产品列表
     */
    @GetMapping("/list")
    public Result<List<ProductionProduct>> list(String productType ) {
        final LambdaQueryWrapper<ProductionProduct> wrapper = Wrappers.lambdaQuery(ProductionProduct.class);
        wrapper.in(ProductionProduct::getProductType, CollUtil.toList("FINISHED_PRODUCT","SEMI_FINISHED_PRODUCT"));
        if(StrUtil.isNotBlank(productType)){
            wrapper.eq(ProductionProduct::getProductType, productType);
        }
        List<ProductionProduct> productList = productionProductService.list(wrapper);
        return Result.ok(productList);
    }
}