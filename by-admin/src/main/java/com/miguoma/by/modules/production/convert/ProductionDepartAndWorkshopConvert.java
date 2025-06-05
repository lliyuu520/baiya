package com.miguoma.by.modules.production.convert;

import com.miguoma.by.modules.production.dto.ProductionDepartAndWorkshopDTO;
import com.miguoma.by.modules.production.entity.ProductionDepartAndWorkshop;
import com.miguoma.by.modules.production.vo.ProductionDepartAndWorkshopVO;
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
public interface ProductionDepartAndWorkshopConvert {
    ProductionDepartAndWorkshopConvert INSTANCE = Mappers.getMapper(ProductionDepartAndWorkshopConvert.class);

    /**
     * 将DTO转换为实体对象
     *
     * @param DepartDTO 工厂DTO
     * @return 工厂实体
     */
    ProductionDepartAndWorkshop convertFromDTO(ProductionDepartAndWorkshopDTO DepartDTO);

    /**
     * 将实体对象转换为VO
     *
     * @param entity 工厂实体
     * @return 工厂VO
     */
    ProductionDepartAndWorkshopVO convertToVO(ProductionDepartAndWorkshop entity);

    /**
     * 将实体对象列表转换为VO列表
     *
     * @param list 工厂实体列表
     * @return 工厂VO列表
     */
    List<ProductionDepartAndWorkshopVO> convertList(List<ProductionDepartAndWorkshop> list);
}
