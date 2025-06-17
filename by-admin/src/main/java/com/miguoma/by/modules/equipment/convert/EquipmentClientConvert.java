package com.miguoma.by.modules.equipment.convert;

import com.miguoma.by.modules.equipment.dto.EquipmentClientDTO;
import com.miguoma.by.modules.equipment.entity.EquipmentClient;
import com.miguoma.by.modules.equipment.vo.EquipmentClientVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Client对象转换器
 */
@Mapper
public interface EquipmentClientConvert {

    EquipmentClientConvert INSTANCE = Mappers.getMapper(EquipmentClientConvert.class);

    /**
     * DTO转实体
     *
     * @param dto DTO对象
     * @return 实体对象
     */
    EquipmentClient convertFromDTO(EquipmentClientDTO dto);

    /**
     * 实体转VO
     *
     * @param entity 实体对象
     * @return VO对象
     */
    EquipmentClientVO convertToVO(EquipmentClient entity);

    /**
     * 实体列表转VO列表
     *
     * @param list 实体列表
     * @return VO列表
     */
    List<EquipmentClientVO> convertList(List<EquipmentClient> list);
}