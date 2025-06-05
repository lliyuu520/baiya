package com.miguoma.by.modules.system.convert;

import com.miguoma.by.modules.system.dto.SysCodeRuleDetailDTO;
import com.miguoma.by.modules.system.entity.SysCodeRuleDetail;
import com.miguoma.by.modules.system.vo.SysCodeRuleDetailVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 编码规则明细对象转换器 用于DTO、Entity、VO之间的转换
 */
@Mapper
public interface SysCodeRuleDetailConvert {

    SysCodeRuleDetailConvert INSTANCE = Mappers.getMapper(SysCodeRuleDetailConvert.class);

    /**
     * 将DTO转换为实体对象
     *
     * @param dto 编码规则明细DTO
     * @return 编码规则明细实体
     */
    SysCodeRuleDetail convertFromDTO(SysCodeRuleDetailDTO dto);

    /**
     * 将实体对象转换为VO
     *
     * @param entity 编码规则明细实体
     * @return 编码规则明细VO
     */
    SysCodeRuleDetailVO convertToVO(SysCodeRuleDetail entity);

    /**
     * 将实体对象列表转换为VO列表
     *
     * @param list 编码规则明细实体列表
     * @return 编码规则明细VO列表
     */
    List<SysCodeRuleDetailVO> convertList(List<SysCodeRuleDetail> list);
} 
