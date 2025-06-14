package com.miguoma.by.modules.record.service;

import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.base.service.BaseService;
import com.miguoma.by.modules.record.entity.RecordQrCode;
import com.miguoma.by.modules.record.query.RecordQrCodeQuery;
import com.miguoma.by.modules.record.vo.RecordQrCodeVO;

import java.util.List;

/**
 * 二维码关联服务接口
 * 提供二维码关联相关的业务操作
 *
 * @author liliangyu
 */
public interface RecordQrCodeService extends BaseService<RecordQrCode> {

    /**
     * 根据二维码集合获取二维码关联集合
     * 
     * @param qrCodeList
     * @return
     */
    List<RecordQrCode> listByQrCode(List<String> qrCodeList);

    /**
     * 分页查询
     * 
     * @param recordQrCodeQuery 查询条件
     * @return 分页结果
     */
    PageVO<RecordQrCodeVO> pageVO(RecordQrCodeQuery recordQrCodeQuery);

    /**
     * 查看二维码信息
     * @param code
     * @return
     */
    RecordQrCodeVO getVOByCode(String code);

    /**
     * 查询成品数量
     *
     * @param finishedProductionOrderId 成品生产订单ID
     * @return 成品数量
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
