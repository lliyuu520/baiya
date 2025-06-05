package com.miguoma.by.modules.production.convert;

import com.miguoma.by.modules.production.dto.ProductionShiftDTO;
import com.miguoma.by.modules.production.entity.ProductionShift;
import com.miguoma.by.modules.production.vo.ProductionShiftVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 产线对象转换器
 * 用于DTO、Entity、VO之间的转换
 *
 * @author liliangyu
 */
@Mapper
public interface ProductionShiftConvert {
    ProductionShiftConvert INSTANCE = Mappers.getMapper(ProductionShiftConvert.class);

    /**
     * 将DTO转换为实体对象
     *
     * @param productionLineDTO 产线DTO
     * @return 产线实体
     */
    ProductionShift convertFromDTO(ProductionShiftDTO productionLineDTO);

    /**
     * 将实体对象转换为VO
     *
     * @param entity 产线实体
     * @return 产线VO
     */
    ProductionShiftVO convertToVO(ProductionShift entity);

    /**
     * 将实体对象列表转换为VO列表
     *
     * @param list 产线实体列表
     * @return 产线VO列表
     */
    List<ProductionShiftVO> convertList(List<ProductionShift> list);
}
