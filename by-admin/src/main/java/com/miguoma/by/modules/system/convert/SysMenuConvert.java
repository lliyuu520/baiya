package com.miguoma.by.modules.system.convert;

import com.miguoma.by.modules.system.dto.SysMenuDTO;
import com.miguoma.by.modules.system.entity.SysMenu;
import com.miguoma.by.modules.system.vo.SysMenuVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SysMenuConvert {
    SysMenuConvert INSTANCE = Mappers.getMapper(SysMenuConvert.class);

    SysMenu convertFromDTO(SysMenuDTO sysMenuDTO);

    SysMenuVO convertToVO(SysMenu entity);

    List<SysMenuVO> convertList(List<SysMenu> list);
}
