package com.miguoma.by.modules.record.service;

import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.base.service.BaseService;
import com.miguoma.by.modules.record.entity.RecordBagCode;
import com.miguoma.by.modules.record.query.RecordBagCodeQuery;
import com.miguoma.by.modules.record.vo.RecordBagCodeVO;

import java.util.List;

/**
 * 袋码关联服务接口
 * 提供袋码关联相关的业务操作
 *
 * @author liliangyu
 */
public interface RecordBagCodeService extends BaseService<RecordBagCode> {

    /**
     * 根据箱码集合获取二维码关联集合
     * 
     * @param boxCode
     * @return
     */
    List<RecordBagCode> listByBoxCode(String boxCode);

    /**
     * 分页查询
     * 
     * @param recordBagCodeQuery 查询条件
     * @return 分页结果
     */
    PageVO<RecordBagCodeVO> pageVO(RecordBagCodeQuery recordBagCodeQuery);

    /**
     * 查询成品条数
     * 
     * @param finishedProductionOrderId 成品生产订单ID
     * @return 成品条数
     */
    Long getCountByFinishedProductionOrderId(Long finishedProductionOrderId);

    /**
     * 查询半成品数量
     *
     * @param semiFinishedProductionOrderId 半成品生产订单ID
     * @return 半成品数量
     */
    Long getCountBySemiFinishedProductionOrderId(Long semiFinishedProductionOrderId);
}
