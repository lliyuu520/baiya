package com.miguoma.by.modules.production.service;

import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.base.service.BaseService;
import com.miguoma.by.modules.production.dto.ProductionTeamDTO;
import com.miguoma.by.modules.production.entity.ProductionTeam;
import com.miguoma.by.modules.production.query.ProductionTeamQuery;
import com.miguoma.by.modules.production.vo.ProductionTeamVO;
import com.miguoma.by.modules.client.vo.TeamInfo;

import java.util.List;


/**
 * 班组service接口
 *
 * @author liliangyu
 */
public interface ProductionTeamService extends BaseService<ProductionTeam> {

    /**
     * 分页查询
     *
     * @param query
     * @return
     */
    PageVO<ProductionTeamVO> pageVO(ProductionTeamQuery query);

    /**
     * 新增
     *
     * @param dto
     */
    void saveOne(ProductionTeamDTO dto);

    /**
     * 编辑
     *
     * @param dto
     */
    void updateOne(ProductionTeamDTO dto);

    /**
     * 查看
     *
     * @param id
     * @return
     */
    ProductionTeamVO getOneById(Long id);

    /**
     * 删除
     *
     * @param id
     */
    void deleteById(Long id);

   
}
