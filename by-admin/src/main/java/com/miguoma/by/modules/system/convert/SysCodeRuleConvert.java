package com.miguoma.by.modules.system.convert;

import com.miguoma.by.modules.system.dto.SysCodeRuleDTO;
import com.miguoma.by.modules.system.entity.SysCodeRule;
import com.miguoma.by.modules.system.vo.SysCodeRuleVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 编码规则转换
 */
@Mapper
public interface SysCodeRuleConvert {
    SysCodeRuleConvert INSTANCE = Mappers.getMapper(SysCodeRuleConvert.class);

    /**
     * 实体转VO
     */
    SysCodeRuleVO convertToVO(SysCodeRule entity);

    /**
     * 实体列表转VO列表
     */
    List<SysCodeRuleVO> convertToList(List<SysCodeRule> list);

    /**
     * DTO转实体
     */
    SysCodeRule convertFromDTO(SysCodeRuleDTO dto);
}