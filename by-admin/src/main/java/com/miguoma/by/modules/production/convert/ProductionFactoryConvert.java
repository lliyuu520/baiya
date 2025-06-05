package com.miguoma.by.modules.production.convert;

import com.miguoma.by.modules.production.dto.ProductionFactoryDTO;
import com.miguoma.by.modules.production.entity.ProductionFactory;
import com.miguoma.by.modules.production.vo.ProductionFactoryVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 工厂对象转换器
 * 用于DTO、Entity、VO之间的转换
 *
 * @author liliangyu
 */
@Mapper
public interface ProductionFactoryConvert {
    ProductionFactoryConvert INSTANCE = Mappers.getMapper(ProductionFactoryConvert.class);

    /**
     * 将DTO转换为实体对象
     *
     * @param factoryDTO 工厂DTO
     * @return 工厂实体
     */
    ProductionFactory convertFromDTO(ProductionFactoryDTO factoryDTO);

    /**
     * 将实体对象转换为VO
     *
     * @param entity 工厂实体
     * @return 工厂VO
     */
    ProductionFactoryVO convertToVO(ProductionFactory entity);

    /**
     * 将实体对象列表转换为VO列表
     *
     * @param list 工厂实体列表
     * @return 工厂VO列表
     */
    List<ProductionFactoryVO> convertList(List<ProductionFactory> list);
}
