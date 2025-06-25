package com.miguoma.by.modules.production.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.miguoma.by.common.base.mapper.IBaseMapper;
import com.miguoma.by.modules.production.entity.ProductionDepartAndWorkshop;
import com.miguoma.by.modules.production.query.ProductionDepartAndWorkshopQuery;
import com.miguoma.by.modules.production.vo.ProductionDepartAndWorkshopVO;

import io.lettuce.core.dynamic.annotation.Param;

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
    /**
     * 分页查询生产部门&车间列表
     *
     * @param page  分页对象
     * @param query 查询条件
     * @return 分页结果
     */

    IPage<ProductionDepartAndWorkshopVO> pageVO(IPage<ProductionDepartAndWorkshop> page,
            @Param("query")ProductionDepartAndWorkshopQuery query);

}
