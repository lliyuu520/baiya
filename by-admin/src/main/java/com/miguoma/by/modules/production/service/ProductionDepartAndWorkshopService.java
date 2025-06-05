package com.miguoma.by.modules.production.service;

import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.base.service.BaseService;
import com.miguoma.by.modules.erp.dto.ErpDepartDTO;
import com.miguoma.by.modules.production.dto.ProductionDepartAndWorkshopDTO;
import com.miguoma.by.modules.production.entity.ProductionDepartAndWorkshop;
import com.miguoma.by.modules.production.query.ProductionDepartAndWorkshopQuery;
import com.miguoma.by.modules.production.vo.ProductionDepartAndWorkshopVO;

import java.util.List;

/**
 * 车间服务接口
 * 提供车间相关的业务操作
 *
 * @author liliangyu
 */
public interface ProductionDepartAndWorkshopService extends BaseService<ProductionDepartAndWorkshop> {

    /**
     * 分页查询车间列表
     *
     * @param query 查询条件
     * @return 分页结果
     */
    PageVO<ProductionDepartAndWorkshopVO> pageVO(ProductionDepartAndWorkshopQuery query);

    /**
     * 新增车间
     *
     * @param dto 车间信息
     */
    void saveOne(ProductionDepartAndWorkshopDTO dto);

    /**
     * 编辑车间
     *
     * @param dto 车间信息
     */
    void updateOne(ProductionDepartAndWorkshopDTO dto);

    /**
     * 获取车间详情
     *
     * @param id 车间ID
     * @return 车间详情
     */
    ProductionDepartAndWorkshopVO getOneById(Long id);

    /**
     * 删除车间
     *
     * @param id 车间ID
     */
    void deleteById(Long id);

    /**
     * 同步生产部门和车间信息
     * @param erpDepartDTOList
     */
    void syncProductionDepartAndWorkShop(List<ErpDepartDTO> erpDepartDTOList);
}
