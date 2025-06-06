package com.miguoma.by.modules.production.service;

import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.base.service.BaseService;
import com.miguoma.by.modules.production.entity.ProductionShift;
import com.miguoma.by.modules.production.query.ProductionShiftQuery;
import com.miguoma.by.modules.production.vo.ProductionShiftVO;

/**
 * 班次服务接口
 * 提供班次相关的业务操作
 *
 * @author liliangyu
 */
public interface ProductionShiftService extends BaseService<ProductionShift> {

    /**
     * 分页查询班次列表
     *
     * @param query 查询条件
     * @return 分页结果
     */
    PageVO<ProductionShiftVO> pageVO(ProductionShiftQuery query);


}
