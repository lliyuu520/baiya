package com.miguoma.by.modules.production.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.miguoma.by.common.annotation.SysLogCut;
import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.enums.SysLogModuleEnums;
import com.miguoma.by.common.enums.SysLogTypeEnums;
import com.miguoma.by.common.utils.Result;
import com.miguoma.by.modules.production.entity.ProductionProduct;
import com.miguoma.by.modules.production.query.ProductionProductQuery;
import com.miguoma.by.modules.production.service.ProductionProductService;
import com.miguoma.by.modules.production.vo.ProductionProductVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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