package com.miguoma.by.modules.production.service;

import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.base.service.BaseService;
import com.miguoma.by.modules.production.entity.ProductionTeam;
import com.miguoma.by.modules.production.query.ProductionTeamQuery;
import com.miguoma.by.modules.production.vo.ProductionTeamVO;

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


}
