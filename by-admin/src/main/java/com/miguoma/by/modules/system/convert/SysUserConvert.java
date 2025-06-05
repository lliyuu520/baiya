package com.miguoma.by.modules.system.convert;

import com.miguoma.by.modules.system.dto.SysUserDTO;
import com.miguoma.by.modules.system.entity.SysUser;
import com.miguoma.by.modules.system.vo.SysUserVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SysUserConvert {
    SysUserConvert INSTANCE = Mappers.getMapper(SysUserConvert.class);

    /**
     * 转换为用户信息
     *
     * @param entity
     * @return
     */
    SysUserVO convertToVO(SysUser entity);

    /**
     * 转换为用户信息
     *
     * @param sysUserDTO
     * @return
     */
    SysUser convertFromDTO(SysUserDTO sysUserDTO);

    List<SysUserVO> convertList(List<SysUser> list);
}
