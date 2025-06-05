package com.miguoma.by.modules.production.convert;


import com.miguoma.by.modules.production.dto.ProductionTeamDTO;
import com.miguoma.by.modules.production.entity.ProductionTeam;
import com.miguoma.by.modules.production.vo.ProductionTeamVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 类型转换器
 */
@Mapper
public interface ProductionTeamConvert {
    ProductionTeamConvert INSTANCE = Mappers.getMapper(ProductionTeamConvert.class);

    /**
     * convertFromDTO
     *
     * @param teamDTO
     * @return
     */
    ProductionTeam convertFromDTO(ProductionTeamDTO teamDTO);

    /**
     * convertToVO
     *
     * @param entity
     * @return
     */
    ProductionTeamVO convertToVO(ProductionTeam entity);

    /**
     * convertList
     *
     * @param list
     * @return
     */
    List<ProductionTeamVO> convertList(List<ProductionTeam> list);

}
