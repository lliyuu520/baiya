package com.miguoma.by.modules.record.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.base.service.impl.BaseServiceImpl;
import com.miguoma.by.modules.record.entity.RecordQrCode;
import com.miguoma.by.modules.record.mapper.RecordQrCodeMapper;
import com.miguoma.by.modules.record.query.RecordQrCodeQuery;
import com.miguoma.by.modules.record.service.RecordQrCodeService;
import com.miguoma.by.modules.record.vo.RecordQrCodeVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 二维码关联服务实现类 实现二维码关联相关的业务操作
 *
 * @author liliangyu
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RecordQrCodeServiceImpl extends BaseServiceImpl<RecordQrCodeMapper, RecordQrCode>
        implements RecordQrCodeService {

    /**
     * * 根据二维码集合获取二维码关联集合
     *
     * @param qrCodeList
     * @return
     */
    @Override
    public List<RecordQrCode> listByQrCode(List<String> qrCodeList) {
        final LambdaQueryWrapper<RecordQrCode> lambdaQuery = Wrappers.lambdaQuery(RecordQrCode.class);
        lambdaQuery.in(RecordQrCode::getCode, qrCodeList);
        return list(lambdaQuery);
    }

    /**
     * 分页查询
     * 
     * @param recordQrCodeQuery 查询条件
     * @return 分页结果
     */
    @Override
    public PageVO<RecordQrCodeVO> pageVO(RecordQrCodeQuery recordQrCodeQuery) {
        final IPage<RecordQrCodeVO> recordQrCodeVOIPage = baseMapper.pageVO(getPage(recordQrCodeQuery),
                recordQrCodeQuery);
        return PageVO.of(recordQrCodeVOIPage);
    }

    /**
     * 查看二维码信息
     *
     * @param code
     * @return
     */
    @Override
    public RecordQrCodeVO getVOByCode(String code) {
        return baseMapper.getVOByCode(code);
    }

    /**
     * 查询成品数量
     *
     * @param finishedProductionOrderId 成品生产订单ID
     * @return 成品数量
     */
    @Override
    public Long getCountByFinishedProductionOrderId(Long finishedProductionOrderId) {
        final LambdaQueryWrapper<RecordQrCode> lambdaQuery = Wrappers.lambdaQuery(RecordQrCode.class);
        lambdaQuery.eq(RecordQrCode::getFinishedProductOrderId, finishedProductionOrderId);
        lambdaQuery.isNotNull(RecordQrCode::getUploadDateTime);
        return count(lambdaQuery);
    }

    /**
     * 查询半成品数量
     *
     * @param semiFinishedProductionOrderId 半成品生产订单ID
     * @return 半成品数量
     */
    @Override
    public Long getCountBySemiFinishedProductionOrderId(Long semiFinishedProductionOrderId) {
        final LambdaQueryWrapper<RecordQrCode> lambdaQuery = Wrappers.lambdaQuery(RecordQrCode.class);
        lambdaQuery.eq(RecordQrCode::getSemiFinishedProductOrderId, semiFinishedProductionOrderId);
        lambdaQuery.isNotNull(RecordQrCode::getUploadDateTime);
        return count(lambdaQuery);
    }
}
