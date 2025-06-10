package com.miguoma.by.modules.production.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.miguoma.by.common.base.mapper.IBaseMapper;
import com.miguoma.by.modules.production.entity.ProductionProduct;
import org.springframework.stereotype.Repository;

/**
 * 产品持久层接口
 * 提供产品相关的数据库操作
 *
 * @author liliangyu
 */
@Repository
public interface ProductionProductMapper extends IBaseMapper<ProductionProduct> {
    
    
    /**
     * 根据编码查询产品
     *
     * @param code 产品编码
     * @return 产品
     */     
    default ProductionProduct getOneByCode(String code) {
        final LambdaQueryWrapper<ProductionProduct> lambdaQuery = Wrappers.lambdaQuery(ProductionProduct.class);
        lambdaQuery.eq(ProductionProduct::getCode, code);
        return this.selectOne(lambdaQuery);
    }

  
}