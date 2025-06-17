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
 * 生产部门&车间服务接口
 * 提供生产部门&车间相关的业务操作
 *
 * @author liliangyu
 */
public interface ProductionDepartAndWorkshopService extends BaseService<ProductionDepartAndWorkshop> {

    /**
     * 分页查询生产部门&车间列表
     *
     * @param query 查询条件
     * @return 分页结果
     */
    PageVO<ProductionDepartAndWorkshopVO> pageVO(ProductionDepartAndWorkshopQuery query);


    /**
     * 同步生产部门和生产部门&车间信息
     * 
     * @param erpDepartDTOList
     */
    void syncProductionDepartAndWorkShop(List<ErpDepartDTO> erpDepartDTOList);


    /**
     * 配置别名
     * @param productionDepartAndWorkshopDTO
     */
    void configAlias(ProductionDepartAndWorkshopDTO productionDepartAndWorkshopDTO);

    /**
     * 配置编码规则
     * @param productionDepartAndWorkshopDTO
     */
    void configCodeRule(ProductionDepartAndWorkshopDTO productionDepartAndWorkshopDTO);

    /**
     * 校验产线编码
     * @param workshopName
     * @return
     */
    Boolean checkWorkshopName(String workshopName);

    /**
     *  根据产线名称获取车间编码列表
     * @param workshopName
     * @return
     */
    List<String> getDepartCodeListByWorkshopName(String workshopName);

    /**
     * 根据编码查询部门&车间信息
     * @param code
     * @return
     */
    ProductionDepartAndWorkshop getOneByCode(String code);
}
