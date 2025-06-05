package com.miguoma.by.modules.production.convert;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.miguoma.by.modules.production.dto.ProductionProductCategoryDTO;
import com.miguoma.by.modules.production.entity.ProductionProductCategory;
import com.miguoma.by.modules.production.vo.ProductionProductCategoryVO;

/**
 * 产品分类对象转换器
 *
 * @author liliangyu
 */
@Mapper
public interface ProductionProductCategoryConvert {

    ProductionProductCategoryConvert INSTANCE = Mappers.getMapper(ProductionProductCategoryConvert.class);

    /**
     * 实体转VO
     *
     * @param entity 实体
     * @return VO
     */
    ProductionProductCategoryVO convertToVO(ProductionProductCategory entity);

    /**
     * 实体列表转VO列表
     *
     * @param entityList 实体列表
     * @return VO列表
     */
    List<ProductionProductCategoryVO> convertList(List<ProductionProductCategory> entityList);

    /**
     * DTO转实体
     *
     * @param dto DTO
     * @return 实体
     */
    ProductionProductCategory convertFromDTO(ProductionProductCategoryDTO dto);
}