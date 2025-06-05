package com.miguoma.by.modules.system.service;

import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.base.service.BaseService;
import com.miguoma.by.modules.system.dto.SysTableConfigDTO;
import com.miguoma.by.modules.system.entity.SysTableConfig;
import com.miguoma.by.modules.system.query.SysTableConfigQuery;
import com.miguoma.by.modules.system.vo.SysTableConfigVO;

import java.util.List;

/**
 * 表配置服务接口
 *
 * @author liliangyu
 */
public interface SysTableConfigService extends BaseService<SysTableConfig> {

    /**
     * 所有表结构
     */
    List<SysTableConfig> listAll();

    /**
     * 添加配置
     *
     * @param tableConfigDTOList
     */
    void saveList(List<SysTableConfigDTO> tableConfigDTOList);

    /**
     * 分页查询
     *
     * @param sysTableConfigQuery
     * @return
     */
    PageVO<SysTableConfigVO> pageVO(SysTableConfigQuery sysTableConfigQuery);

    /**
     * 详情
     *
     * @param id
     * @return
     */
    SysTableConfigVO getOneById(Long id);

    /**
     * 所有表结构VO
     *
     * @return
     */
    List<SysTableConfigVO> listAllVO();
}
