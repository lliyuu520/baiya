package com.miguoma.by.modules.record.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.miguoma.by.common.base.service.impl.BaseServiceImpl;
import com.miguoma.by.modules.record.entity.RecordBagCode;
import com.miguoma.by.modules.record.mapper.RecordBagCodeMapper;
import com.miguoma.by.modules.record.service.RecordBagCodeService;
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
public class RecordBagCodeServiceImpl extends BaseServiceImpl<RecordBagCodeMapper, RecordBagCode> implements RecordBagCodeService {

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
}
