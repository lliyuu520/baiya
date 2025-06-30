package com.miguoma.by.modules.equipment.convert;

import com.miguoma.by.modules.equipment.dto.EquipmentApkDTO;
import com.miguoma.by.modules.equipment.entity.EquipmentApk;
import com.miguoma.by.modules.equipment.vo.EquipmentApkVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * APK对象转换器
 */
@Mapper
public interface EquipmentApkConvert {

    EquipmentApkConvert INSTANCE = Mappers.getMapper(EquipmentApkConvert.class);

    /**
     * DTO转实体
     *
     * @param dto DTO对象
     * @return 实体对象
     */
    EquipmentApk convertFromDTO(EquipmentApkDTO dto);

    /**
     * 实体转VO
     *
     * @param entity 实体对象
     * @return VO对象
     */
    EquipmentApkVO convertToVO(EquipmentApk entity);

    /**
     * 实体列表转VO列表
     *
     * @param list 实体列表
     * @return VO列表
     */
    List<EquipmentApkVO> convertList(List<EquipmentApk> list);
}