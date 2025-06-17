package com.miguoma.by.modules.system.convert;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.miguoma.by.modules.system.dto.SysApkDTO;
import com.miguoma.by.modules.system.entity.SysApk;
import com.miguoma.by.modules.system.vo.SysApkVO;

/**
 * APK对象转换器
 */
@Mapper
public interface SysApkConvert {

    SysApkConvert INSTANCE = Mappers.getMapper(SysApkConvert.class);

    /**
     * DTO转实体
     *
     * @param dto DTO对象
     * @return 实体对象
     */
    SysApk convertFromDTO(SysApkDTO dto);

    /**
     * 实体转VO
     *
     * @param entity 实体对象
     * @return VO对象
     */
    SysApkVO convertToVO(SysApk entity);

    /**
     * 实体列表转VO列表
     *
     * @param list 实体列表
     * @return VO列表
     */
    List<SysApkVO> convertList(List<SysApk> list);
}