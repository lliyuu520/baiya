package com.miguoma.by.modules.production.convert;


import com.miguoma.by.modules.production.dto.ProductionProductDTO;
import com.miguoma.by.modules.production.entity.ProductionProduct;
import com.miguoma.by.modules.production.vo.ProductionProductVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 产品对象转换器
 * 用于DTO、Entity、VO之间的转换
 *
 * @author liliangyu
 */
@Mapper
public interface ProductionProductConvert {
    ProductionProductConvert INSTANCE = Mappers.getMapper(ProductionProductConvert.class);

    /**
     * 将DTO转换为实体对象
     *
     * @param productDTO 产品DTO
     * @return 产品实体
     */
    ProductionProduct convertFromDTO(ProductionProductDTO productDTO);

    /**
     * 将实体对象转换为VO
     *
     * @param entity 产品实体
     * @return 产品VO
     */
    ProductionProductVO convertToVO(ProductionProduct entity);

    /**
     * 将实体对象列表转换为VO列表
     *
     * @param list 产品实体列表
     * @return 产品VO列表
     */
    List<ProductionProductVO> convertList(List<ProductionProduct> list);
}
