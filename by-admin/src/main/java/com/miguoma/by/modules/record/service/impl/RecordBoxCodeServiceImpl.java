package com.miguoma.by.modules.record.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.miguoma.by.common.base.service.impl.BaseServiceImpl;
import com.miguoma.by.modules.record.entity.RecordBoxCode;
import com.miguoma.by.modules.record.mapper.RecordBoxCodeMapper;
import com.miguoma.by.modules.record.service.RecordBoxCodeService;
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
public class RecordBoxCodeServiceImpl extends BaseServiceImpl<RecordBoxCodeMapper, RecordBoxCode> implements RecordBoxCodeService {
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
     * @param boxCodeList1
     * @return
     */
    @Override
    public List<RecordBoxCode> listByBoxCode(List<String> boxCodeList1) {
        final LambdaQueryWrapper<RecordBoxCode> lambdaQuery = Wrappers.lambdaQuery(RecordBoxCode.class);
        lambdaQuery.in(RecordBoxCode::getCode, boxCodeList1);
        return list(lambdaQuery);
    }
}
