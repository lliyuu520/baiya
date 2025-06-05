package com.miguoma.by.modules.record.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.miguoma.by.common.base.service.impl.BaseServiceImpl;
import com.miguoma.by.modules.record.entity.RecordQrCode;
import com.miguoma.by.modules.record.mapper.RecordQrCodeMapper;
import com.miguoma.by.modules.record.service.RecordQrCodeService;
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
public class RecordQrCodeServiceImpl extends BaseServiceImpl<RecordQrCodeMapper, RecordQrCode> implements RecordQrCodeService {


    /**
     * * 根据二维码集合获取二维码关联集合
     *
     * @param qrCodeList
     * @return
     */
    @Override
    public List<RecordQrCode> listByQrCode(List<String> qrCodeList) {
        final LambdaQueryWrapper<RecordQrCode> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.in(RecordQrCode::getCode, qrCodeList);
        return list(lambdaQuery);
    }
}
