package com.miguoma.by.modules.system.convert;

import com.miguoma.by.modules.system.dto.SysTableFieldDTO;
import com.miguoma.by.modules.system.entity.SysTableField;
import com.miguoma.by.modules.system.vo.SysTableFieldVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 字段配置转换
 *
 * @author liliangyu
 */
@Mapper
public interface TableFieldConvert {

    /**
     * 实例
     */
    TableFieldConvert INSTANCE = Mappers.getMapper(TableFieldConvert.class);

    /**
     * 转换为VO
     *
     * @param sysTableField
     * @return
     */
    SysTableFieldVO convertToVO(SysTableField sysTableField);

    /**
     * 转换为实体
     *
     * @param sysTableFieldDTO
     * @return
     */
    SysTableField convertFromDTO(SysTableFieldDTO sysTableFieldDTO);

    /**
     * 转换为列表
     *
     * @param records
     * @return
     */
    List<SysTableFieldVO> convertToList(List<SysTableField> records);
}
