package com.miguoma.by.modules.production.service;

import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.base.service.BaseService;
import com.miguoma.by.modules.erp.dto.ErpProductDTO;
import com.miguoma.by.modules.production.entity.ProductionProduct;
import com.miguoma.by.modules.production.query.ProductionProductQuery;
import com.miguoma.by.modules.production.vo.ProductionProductVO;

import java.util.List;

/**
 * 产品服务接口 提供产品相关的业务操作
 *
 * @author liliangyu
 */
public interface ProductionProductService extends BaseService<ProductionProduct> {

    /**
     * 分页查询产品列表
     *
     * @param query 查询条件
     * @return 分页结果
     */
    PageVO<ProductionProductVO> pageVO(ProductionProductQuery query);


    /**
     * 同步ERP产品信息
     * 
     * @param productList
     */
    void syncProduct(List<ErpProductDTO> productList);
}