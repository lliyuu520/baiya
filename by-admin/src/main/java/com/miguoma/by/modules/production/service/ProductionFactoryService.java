package com.miguoma.by.modules.production.service;

import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.base.service.BaseService;
import com.miguoma.by.modules.production.entity.ProductionFactory;
import com.miguoma.by.modules.production.query.ProductionFactoryQuery;
import com.miguoma.by.modules.production.vo.ProductionFactoryVO;

/**
 * 工厂服务接口
 * 提供工厂相关的业务操作
 *
 * @author liliangyu
 */
public interface ProductionFactoryService extends BaseService<ProductionFactory> {

    /**
     * 分页查询工厂列表
     *
     * @param query 查询条件
     * @return 分页结果
     */
    PageVO<ProductionFactoryVO> pageVO(ProductionFactoryQuery query);


}
