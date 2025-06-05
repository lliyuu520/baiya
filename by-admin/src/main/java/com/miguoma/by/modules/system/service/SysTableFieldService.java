package com.miguoma.by.modules.system.service;

import com.miguoma.by.common.base.service.BaseService;
import com.miguoma.by.modules.system.dto.SysTableFieldDTO;
import com.miguoma.by.modules.system.entity.SysTableField;
import com.miguoma.by.modules.system.vo.SysTableFieldVO;

import java.util.List;

/**
 * 字段配置服务接口
 *
 * @author liliangyu
 */
public interface SysTableFieldService extends BaseService<SysTableField> {

    /**
     * 保存字段配置
     *
     * @param sysTableFieldDTO
     */
    void saveOne(SysTableFieldDTO sysTableFieldDTO);

    /**
     * 根据表配置id查询字段配置信息
     *
     * @param tableConfigId 表配置id
     * @return 字段配置信息
     */
    List<SysTableFieldVO> listByTableConfigId(Long tableConfigId);

    /**
     * 根据表配置id查询字段配置信息
     *
     * @param tableConfigId
     * @return
     */
    List<String> getOneByTableConfigId(Long tableConfigId);


}
