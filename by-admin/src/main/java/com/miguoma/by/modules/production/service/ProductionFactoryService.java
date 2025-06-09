package com.miguoma.by.modules.production.service;

import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.base.service.BaseService;
import com.miguoma.by.modules.production.dto.ProductionFactoryDTO;
import com.miguoma.by.modules.production.entity.ProductionFactory;
import com.miguoma.by.modules.production.query.ProductionFactoryQuery;
import com.miguoma.by.modules.production.vo.ProductionFactoryVO;
import com.miguoma.by.modules.client.vo.FactoryInfo;

import java.util.List;

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

    /**
     * 新增工厂
     *
     * @param dto 工厂信息
     */
    void saveOne(ProductionFactoryDTO dto);

    /**
     * 编辑工厂
     *
     * @param dto 工厂信息
     */
    void updateOne(ProductionFactoryDTO dto);

    /**
     * 获取工厂详情
     *
     * @param id 工厂ID
     * @return 工厂详情
     */
    ProductionFactoryVO getOneById(Long id);

    /**
     * 删除工厂
     *
     * @param id 工厂ID
     */
    void deleteById(Long id);

    /**
     * 校验工厂编码是否存在
     * @param factoryCode
     * @return
     */
    Boolean checkFactoryCode(String factoryCode);

}
