package com.miguoma.by.modules.production.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.miguoma.by.common.base.mapper.IBaseMapper;
import com.miguoma.by.modules.production.entity.ProductionOrder;
import com.miguoma.by.modules.production.query.ProductionOrderQuery;
import com.miguoma.by.modules.production.vo.ProductionOrderVO;

/**
 * 生产订单持久层接口
 * 提供生产订单相关的数据库操作
 *
 * @author liliangyu
 */
@Repository
public interface ProductionOrderMapper extends IBaseMapper<ProductionOrder> {

    /**
     * 根据订单号查询订单
     *
     * @param orderNo 订单号
     * @return 订单
     */
    default ProductionOrder getOneByOrderNo(String orderNo) {
        LambdaQueryWrapper<ProductionOrder> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ProductionOrder::getOrderNo, orderNo);
        return selectOne(wrapper);
    }

    /**
     * 分页查询生产订单列表
     *
     * @param page 分页对象
     * @param query 查询条件
     * @return 分页结果 
     */
    IPage<ProductionOrderVO> pageVO(IPage<ProductionOrder> page, @Param("query") ProductionOrderQuery query);

    /**
     * 查询生产订单列表
     * @param page
     * @param query
     * @return
     */
    List<ProductionOrderVO> listVO( @Param("query") ProductionOrderQuery query);
}