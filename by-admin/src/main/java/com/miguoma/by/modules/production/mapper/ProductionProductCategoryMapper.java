package com.miguoma.by.modules.production.mapper;

import java.util.HashSet;
import java.util.Set;

import com.miguoma.by.modules.production.enums.ProductTypeEnum;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.miguoma.by.common.base.mapper.IBaseMapper;
import com.miguoma.by.modules.production.entity.ProductionProductCategory;

import cn.hutool.core.util.StrUtil;

/**
 * 产品分类持久层接口
 * 提供产品分类相关的数据库操作
 *
 * @author liliangyu
 */
@Repository
public interface ProductionProductCategoryMapper extends IBaseMapper<ProductionProductCategory> {

    /** 根据分类编码查询分类 */
    default ProductionProductCategory selectByCode(String code) {
        LambdaQueryWrapper<ProductionProductCategory> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(ProductionProductCategory::getCode, code);
        return selectOne(lambdaQuery);
    }

    /**
     * 查询产品类型
     * FINISHED_PRODUCT 成品 10
     * SEMI_FINISHED_PRODUCT 半成品 15
     *
     * @param categoryCode 分类编码
     * @return 产品类型
     */
    default String getProductType(String categoryCode) {
        return getProductTypeWithDepth(categoryCode, new HashSet<>(), 0);
    }

    /**
     * 带深度限制和循环检测的产品类型查询
     *
     * @param categoryCode 分类编码
     * @param visitedCodes 已访问的编码集合，用于检测循环
     * @param depth        当前递归深度
     * @return 产品类型
     */
    default String getProductTypeWithDepth(String categoryCode, Set<String> visitedCodes, int depth) {
        // 检查输入参数和递归终止条件
        if (StrUtil.isBlank(categoryCode) || visitedCodes.contains(categoryCode) || depth > 10) {
            return null;
        }

        visitedCodes.add(categoryCode);

        // 查询分类信息
        final ProductionProductCategory productionProductCategory = selectByCode(categoryCode);
        if (productionProductCategory == null) {
            return null;
        }

        // 检查当前分类编码
        final String code = productionProductCategory.getCode();
        if (StrUtil.equals("10", code)) {
            return ProductTypeEnum.FINISHED_PRODUCT.getCode();
        }
        if (StrUtil.equals("15", code)) {
            return ProductTypeEnum.SEMI_FINISHED_PRODUCT.getCode();
        }
        // 递归查询父级分类
        final String parentCode = productionProductCategory.getParentCode();
        if (StrUtil.isBlank(parentCode)) {
            return null;
        }

        return getProductTypeWithDepth(parentCode, visitedCodes, depth + 1);
    }
}