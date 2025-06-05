package com.miguoma.by.modules.system.convert;

import com.miguoma.by.modules.system.dto.SysTableConfigDTO;
import com.miguoma.by.modules.system.entity.SysTableConfig;
import com.miguoma.by.modules.system.vo.SysTableConfigVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TableConfigConvert {

    /**
     * 实例
     */
    TableConfigConvert INSTANCE = Mappers.getMapper(TableConfigConvert.class);

    /**
     * 转换为DTO
     *
     * @param sysTableConfig
     * @return
     */
    SysTableConfigVO convertToVO(SysTableConfig sysTableConfig);

    /**
     * 转换为实体
     *
     * @param tableConfigDTO
     * @return
     */
    SysTableConfig convertFromDTO(SysTableConfigDTO tableConfigDTO);

    /**
     * 转换为列表
     *
     * @param records
     * @return
     */
    List<SysTableConfigVO> convertToList(List<SysTableConfig> records);
}
