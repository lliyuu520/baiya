package com.miguoma.by.modules.production.convert;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.miguoma.by.modules.production.dto.ProductionOrderDTO;
import com.miguoma.by.modules.production.entity.ProductionOrder;
import com.miguoma.by.modules.production.vo.ProductionOrderVO;

/**
 * 生产订单对象转换器
 *
 * @author liliangyu
 */
@Mapper
public interface ProductionOrderConvert {

    ProductionOrderConvert INSTANCE = Mappers.getMapper(ProductionOrderConvert.class);

    /**
     * 实体转VO
     *
     * @param entity 实体
     * @return VO
     */
    ProductionOrderVO convertToVO(ProductionOrder entity);

    /**
     * 实体列表转VO列表
     *
     * @param entityList 实体列表
     * @return VO列表
     */
    List<ProductionOrderVO> convertList(List<ProductionOrder> entityList);

    /**
     * DTO转实体
     *
     * @param dto DTO
     * @return 实体
     */
    ProductionOrder convertFromDTO(ProductionOrderDTO dto);
}