package com.miguoma.by.modules.record.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.base.service.impl.BaseServiceImpl;
import com.miguoma.by.common.satoken.user.UserDetail;
import com.miguoma.by.common.utils.SysUserUtil;
import com.miguoma.by.modules.client.dto.RecordQrCodeReplaceDTO;
import com.miguoma.by.modules.record.entity.RecordQrCode;
import com.miguoma.by.modules.record.entity.RecordQrCodeReplace;
import com.miguoma.by.modules.record.enums.ReplaceHandleFlagEnums;
import com.miguoma.by.modules.record.mapper.RecordQrCodeMapper;
import com.miguoma.by.modules.record.mapper.RecordQrCodeReplaceMapper;
import com.miguoma.by.modules.record.query.RecordQrCodeReplaceQuery;
import com.miguoma.by.modules.record.service.RecordQrCodeReplaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 二维码替换记录服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RecordQrCodeReplaceServiceImpl extends BaseServiceImpl<RecordQrCodeReplaceMapper, RecordQrCodeReplace> implements RecordQrCodeReplaceService {
    private final RecordQrCodeMapper recordQrCodeMapper;

    /**
     * 分页查询
     */
    @Override
    public PageVO<RecordQrCodeReplace> pageVO(RecordQrCodeReplaceQuery query) {

        IPage<RecordQrCodeReplace> resultPage = this.page(getPage(query), buildWrapper(query));

        return PageVO.of(resultPage);
    }

    /**
     * 构建查询条件
     *
     * @param query
     * @return
     */
    private LambdaQueryWrapper<RecordQrCodeReplace> buildWrapper(RecordQrCodeReplaceQuery query) {
        LambdaQueryWrapper<RecordQrCodeReplace> lambdaQuery = Wrappers.lambdaQuery(RecordQrCodeReplace.class);
        String originalQrCode = query.getOriginalQrCode();
        if (StrUtil.isNotBlank(originalQrCode)) {
            lambdaQuery.eq(RecordQrCodeReplace::getOriginalQrCode, originalQrCode);
        }
        String replaceQrCode = query.getReplaceQrCode();
        if (StrUtil.isNotBlank(replaceQrCode)) {
            lambdaQuery.eq(RecordQrCodeReplace::getReplaceQrCode, replaceQrCode);
        }
        String handleFlag = query.getHandleFlag();
        if (StrUtil.isNotBlank(handleFlag)) {
            lambdaQuery.eq(RecordQrCodeReplace::getHandleFlag, handleFlag);
        }
        final LocalDateTime submitDateTimeBegin = query.getSubmitDateTimeBegin();
        if (submitDateTimeBegin != null) {
            lambdaQuery.ge(RecordQrCodeReplace::getSubmitDatetime, submitDateTimeBegin);
        }
        final LocalDateTime submitDateTimeEnd = query.getSubmitDateTimeEnd();
        if (submitDateTimeEnd != null) {
            lambdaQuery.le(RecordQrCodeReplace::getSubmitDatetime, submitDateTimeEnd);
        }
        lambdaQuery.orderByDesc(RecordQrCodeReplace::getId);
        return lambdaQuery;
    }

    /**
     * 保存一条记录
     *
     * @param recordQrCodeReplaceDTO
     */

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOne(RecordQrCodeReplaceDTO recordQrCodeReplaceDTO) {
        UserDetail userInfo = SysUserUtil.getUserInfo();
        Long userId = userInfo.getId();
        String username = userInfo.getUsername();
        final LocalDateTime now = LocalDateTimeUtil.now();

        String originalQrCode = recordQrCodeReplaceDTO.getOriginalQrCode();
        String replaceQrCode = recordQrCodeReplaceDTO.getReplaceQrCode();
        final RecordQrCode oneByCode = recordQrCodeMapper.getOneByCode(replaceQrCode);
        RecordQrCode recordQrCode = recordQrCodeMapper.getOneByCode(originalQrCode);

        RecordQrCodeReplace recordQrCodeReplace = new RecordQrCodeReplace();
        recordQrCodeReplace.setSubmitUserId(userId);
        recordQrCodeReplace.setSubmitUsername(username);
        recordQrCodeReplace.setSubmitDatetime(now);
        recordQrCodeReplace.setHandleFlag(ReplaceHandleFlagEnums.WAITING.getCode());
        recordQrCodeReplace.setOriginalQrCode(originalQrCode);
        recordQrCodeReplace.setReplaceQrCode(replaceQrCode);
        if(oneByCode != null){
            recordQrCodeReplace.setHandleFlag(ReplaceHandleFlagEnums.FAIL.getCode());
            recordQrCodeReplace.setFailReason("替换二维码已存在");
            save(recordQrCodeReplace);
            return;
        }

        if (recordQrCode != null) {
            recordQrCodeReplace.setHandleFlag(ReplaceHandleFlagEnums.SUCCESS.getCode());
            recordQrCodeReplace.setHandleDatetime(now);
            recordQrCode.setCode(replaceQrCode);
            recordQrCodeMapper.updateById(recordQrCode);
        }
        this.save(recordQrCodeReplace);
    }


    /**
     * 查询未处理的记录
     *
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void HandleNotHandleData() {
        final LocalDateTime now = LocalDateTimeUtil.now();
        final LambdaQueryWrapper<RecordQrCodeReplace> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(RecordQrCodeReplace::getHandleFlag, ReplaceHandleFlagEnums.WAITING.getCode());
        // 7天内的
        lambdaQuery.ge(RecordQrCodeReplace::getSubmitDatetime, now.minusDays(7));
        lambdaQuery.orderByAsc(RecordQrCodeReplace::getId);
        lambdaQuery.last("AND ROWNUM <= 50");
        final List<RecordQrCodeReplace> recordQrCodeReplaceList = list(lambdaQuery);
        if (CollUtil.isEmpty(recordQrCodeReplaceList)) {
            log.info("没有未处理的数据");
            return;
        }

        recordQrCodeReplaceList.forEach(recordQrCodeReplace -> {
            final RecordQrCodeReplaceDTO recordQrCodeReplaceDTO = new RecordQrCodeReplaceDTO();
            recordQrCodeReplaceDTO.setOriginalQrCode(recordQrCodeReplace.getOriginalQrCode());
            recordQrCodeReplaceDTO.setReplaceQrCode(recordQrCodeReplace.getReplaceQrCode());
            recordQrCodeReplaceDTO.setReplaceReason(recordQrCodeReplace.getReplaceReason());
            saveOne(recordQrCodeReplaceDTO);
        });
    }
}