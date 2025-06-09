package com.miguoma.by.modules.production.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.miguoma.by.common.base.mapper.IBaseMapper;
import com.miguoma.by.modules.production.entity.ProductionDepartAndWorkshop;
import org.springframework.stereotype.Repository;

/**
 * 生产部门&车间持久层接口
 * 提供生产部门&车间相关的数据库操作
 *
 * @author liliangyu
 */
@Repository
public interface ProductionDepartAndWorkshopMapper extends IBaseMapper<ProductionDepartAndWorkshop> {
    /**
     *  根据编码查询信息
     * @param code
     * @return
     */
    default ProductionDepartAndWorkshop getOneByCode(String code){
        final LambdaQueryWrapper<ProductionDepartAndWorkshop> lambdaQuery = Wrappers.lambdaQuery(ProductionDepartAndWorkshop.class);
        lambdaQuery.eq(ProductionDepartAndWorkshop::getCode, code);
        return selectOne(lambdaQuery);

    }

}
