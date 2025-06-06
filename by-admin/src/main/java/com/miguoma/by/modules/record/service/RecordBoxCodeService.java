 package com.miguoma.by.modules.record.service;

import java.util.List;

import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.base.service.BaseService;
import com.miguoma.by.modules.record.entity.RecordBoxCode;
import com.miguoma.by.modules.record.query.RecordBoxCodeQuery;
import com.miguoma.by.modules.record.vo.RecordBoxCodeVO;

 /**
 * 二维码关联服务接口
 * 提供二维码关联相关的业务操作
 *
 * @author liliangyu
 */
public interface RecordBoxCodeService extends BaseService<RecordBoxCode> {

     /**
      * 获取二维码关联详情
     * 
      * @param boxCode
      * @return
      */
     RecordBoxCode getOneByBoxCode(String boxCode);

     /**
     * * 根据箱码集合获取二维码关联集合
     * 
      * @param boxCodeList
      * @return
      */
     List<RecordBoxCode> listByBoxCode(List<String> boxCodeList);

    /**
     * 分页查询
     * 
     * @param recordBoxCodeQuery
     * @return
     */
     PageVO<RecordBoxCodeVO> pageVO(RecordBoxCodeQuery recordBoxCodeQuery);

     /**
      * 查询成品数量
     * 
      * @param finishedProductionOrderId
      * @return
      */
     Long getCountByFinishedProductionOrderId(Long finishedProductionOrderId);

     /**
      * 查询半成品数量
     *
     * @param semiFinishedProductionOrderId
      */
     Long getCountBySemiFinishedProductionOrderId(Long semiFinishedProductionOrderId);

 } 
