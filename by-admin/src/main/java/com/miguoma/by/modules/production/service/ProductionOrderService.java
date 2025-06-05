package com.miguoma.by.modules.production.service;

import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.base.service.BaseService;
import com.miguoma.by.modules.client.dto.PullCodeDTO;
import com.miguoma.by.modules.client.dto.RecordCodeUploadDTO;
import com.miguoma.by.modules.client.vo.PullCodeVO;
import com.miguoma.by.modules.erp.dto.ErpOrderDTO;
import com.miguoma.by.modules.production.dto.ProductionOrderDTO;
import com.miguoma.by.modules.production.entity.ProductionOrder;
import com.miguoma.by.modules.production.query.ProductionOrderQuery;
import com.miguoma.by.modules.production.vo.ProductionOrderVO;

import java.util.List;

/**
 * 生产订单服务接口
 * 提供生产订单相关的业务操作
 *
 * @author liliangyu
 */
public interface ProductionOrderService extends BaseService<ProductionOrder> {

    /**
     * 分页查询生产订单列表
     *
     * @param query 查询条件
     * @return 分页结果
     */
    PageVO<ProductionOrderVO> pageVO(ProductionOrderQuery query);
    
    

    /**
     * 新增生产订单
     *
     * @param dto 生产订单信息
     */
    void saveOne(ProductionOrderDTO dto);

    /**
     * 编辑生产订单
     *
     * @param dto 生产订单信息
     */
    void updateOne(ProductionOrderDTO dto);

    /**
     * 获取生产订单详情
     *
     * @param id 生产订单ID
     * @return 生产订单详情
     */
    ProductionOrderVO getOneById(Long id);

    /**
     * 删除生产订单
     *
     * @param id 生产订单ID
     */
    void deleteById(Long id);

 
    /**
     * 返工
     *
     * @param id 生产订单ID
     */
    void rework(Long id);

    /**
     * 取消返工
     *
     * @param id 生产订单ID
     */
    void cancelRework(Long id);

    /**
     * 拉码
     * @param pullCodeDTO
     * @return
     */
    PullCodeVO pullCode(PullCodeDTO pullCodeDTO);

    /**
     * 采集上传
     * @param recordCodeUploadDTO
     */
    void collectUpload(RecordCodeUploadDTO recordCodeUploadDTO);

    /**
     * 列表
     * @param query
     * @return
     */
    List<ProductionOrderVO> listVO(ProductionOrderQuery query);

    /**
     * erp订单同步
     * @param erpOrderDTO 
     */
    void syncOrder(ErpOrderDTO erpOrderDTO);
}