package com.miguoma.by.modules.record.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.base.service.impl.BaseServiceImpl;
import com.miguoma.by.modules.record.entity.RecordBagCode;
import com.miguoma.by.modules.record.mapper.RecordBagCodeMapper;
import com.miguoma.by.modules.record.query.RecordBagCodeQuery;
import com.miguoma.by.modules.record.service.RecordBagCodeService;
import com.miguoma.by.modules.record.vo.RecordBagCodeVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 二维码关联服务实现类 实现二维码关联相关的业务操作
 *
 * @author liliangyu
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RecordBagCodeServiceImpl extends BaseServiceImpl<RecordBagCodeMapper, RecordBagCode>
        implements RecordBagCodeService {

    /**
     * * 根据箱码集合获取二维码关联集合
     *
     * @param boxCode
     * @return
     */
    @Override
    public List<RecordBagCode> listByBoxCode(String boxCode) {
        final LambdaQueryWrapper<RecordBagCode> lambdaQuery = Wrappers.lambdaQuery(RecordBagCode.class);
        lambdaQuery.eq(RecordBagCode::getBoxCode, boxCode);
        return list(lambdaQuery);

    }

    /**
     * 分页查询
     * 
     * @param recordBagCodeQuery 查询条件
     * @return 分页结果
     */
    @Override
    public PageVO<RecordBagCodeVO> pageVO(RecordBagCodeQuery recordBagCodeQuery) {
        final IPage<RecordBagCodeVO> recordBagCodeVOIPage = baseMapper.pageVO(getPage(recordBagCodeQuery),
                recordBagCodeQuery);
        return PageVO.of(recordBagCodeVOIPage);
    }

    /**
     * 查询成品数量
     *
     * @param finishedProductionOrderId 成品生产订单ID
     * @return 成品数量
     */
    @Override
    public Long getCountByFinishedProductionOrderId(Long finishedProductionOrderId) {
        final LambdaQueryWrapper<RecordBagCode> lambdaQuery = Wrappers.lambdaQuery(RecordBagCode.class);
        lambdaQuery.eq(RecordBagCode::getFinishedProductOrderId, finishedProductionOrderId);
        lambdaQuery.isNotNull(RecordBagCode::getUploadDateTime);
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
        final LambdaQueryWrapper<RecordBagCode> lambdaQuery = Wrappers.lambdaQuery(RecordBagCode.class);
        lambdaQuery.eq(RecordBagCode::getSemiFinishedProductOrderId, semiFinishedProductionOrderId);
        lambdaQuery.isNotNull(RecordBagCode::getUploadDateTime);
        return count(lambdaQuery);
    }
}
