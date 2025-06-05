package com.miguoma.by.modules.record.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.base.service.impl.BaseServiceImpl;
import com.miguoma.by.modules.record.entity.RecordBoxCode;
import com.miguoma.by.modules.record.mapper.RecordBoxCodeMapper;
import com.miguoma.by.modules.record.query.RecordBoxCodeQuery;
import com.miguoma.by.modules.record.service.RecordBoxCodeService;
import com.miguoma.by.modules.record.vo.RecordBoxCodeVO;

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
public class RecordBoxCodeServiceImpl extends BaseServiceImpl<RecordBoxCodeMapper, RecordBoxCode>
        implements RecordBoxCodeService {
    /**
     * 获取二维码关联详情
     *
     * @param boxCode
     * @return
     */
    @Override
    public RecordBoxCode getOneByBoxCode(String boxCode) {
        final LambdaQueryWrapper<RecordBoxCode> lambdaQuery = Wrappers.lambdaQuery(RecordBoxCode.class);
        lambdaQuery.eq(RecordBoxCode::getCode, boxCode);
        return getOne(lambdaQuery);
    }

    /**
     * * 根据箱码集合获取二维码关联集合
     *
     * @param boxCodeList
     * @return
     */
    @Override
    public List<RecordBoxCode> listByBoxCode(List<String> boxCodeList) {
        final LambdaQueryWrapper<RecordBoxCode> lambdaQuery = Wrappers.lambdaQuery(RecordBoxCode.class);
        lambdaQuery.in(RecordBoxCode::getCode, boxCodeList);
        return list(lambdaQuery);
    }

    /**
     * 分页查询
     * 
     * @param recordBoxCodeQuery
     * @return
     */
    @Override
    public PageVO<RecordBoxCodeVO> pageVO(RecordBoxCodeQuery recordBoxCodeQuery) {
        final IPage<RecordBoxCodeVO> recordBoxCodeVOIPage = baseMapper.pageVO(getPage(recordBoxCodeQuery),
                recordBoxCodeQuery);
        return PageVO.of(recordBoxCodeVOIPage);
    }

    /**
     * 查询成品数量
     * 
     * @param finishedProductionOrderId
     * @return
     */
    @Override
    public Long getCountByFinishedProductionOrderId(Long finishedProductionOrderId) {
        final LambdaQueryWrapper<RecordBoxCode> lambdaQuery = Wrappers.lambdaQuery(RecordBoxCode.class);
        lambdaQuery.eq(RecordBoxCode::getFinishedProductOrderId, finishedProductionOrderId);
        lambdaQuery.isNotNull(RecordBoxCode::getUploadDateTime);
        return count(lambdaQuery);
    }

    /**
     * 查询半成品数量
     *
     * @param semiFinishedProductionOrderId
     */
    @Override
    public Long getCountBySemiFinishedProductionOrderId(Long semiFinishedProductionOrderId) {
        final LambdaQueryWrapper<RecordBoxCode> lambdaQuery = Wrappers.lambdaQuery(RecordBoxCode.class);
        lambdaQuery.eq(RecordBoxCode::getSemiFinishedProductOrderId, semiFinishedProductionOrderId);
        lambdaQuery.isNotNull(RecordBoxCode::getUploadDateTime);
        return count(lambdaQuery);
    }
}
