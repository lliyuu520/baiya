package com.miguoma.by.modules.production.service;

import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.base.service.BaseService;
import com.miguoma.by.modules.production.dto.ProductionShiftDTO;
import com.miguoma.by.modules.production.entity.ProductionShift;
import com.miguoma.by.modules.production.query.ProductionShiftQuery;
import com.miguoma.by.modules.production.vo.ProductionShiftVO;

/**
 * 产线服务接口
 * 提供产线相关的业务操作
 *
 * @author liliangyu
 */
public interface ProductionShiftService extends BaseService<ProductionShift> {

    /**
     * 分页查询产线列表
     *
     * @param query 查询条件
     * @return 分页结果
     */
    PageVO<ProductionShiftVO> pageVO(ProductionShiftQuery query);

    /**
     * 新增产线
     *
     * @param dto 产线信息
     */
    void saveOne(ProductionShiftDTO dto);

    /**
     * 编辑产线
     *
     * @param dto 产线信息
     */
    void updateOne(ProductionShiftDTO dto);

    /**
     * 获取产线详情
     *
     * @param id 产线ID
     * @return 产线详情
     */
    ProductionShiftVO getOneById(Long id);

    /**
     * 删除产线
     *
     * @param id 产线ID
     */
    void deleteById(Long id);
}
