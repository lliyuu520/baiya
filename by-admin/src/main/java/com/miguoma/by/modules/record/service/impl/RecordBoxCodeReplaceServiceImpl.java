package com.miguoma.by.modules.record.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.base.service.impl.BaseServiceImpl;
import com.miguoma.by.common.satoken.user.UserDetail;
import com.miguoma.by.common.utils.SysUserUtil;
import com.miguoma.by.modules.client.dto.RecordBoxCodeReplaceDTO;
import com.miguoma.by.modules.record.entity.RecordBoxCode;
import com.miguoma.by.modules.record.entity.RecordBoxCodeReplace;
import com.miguoma.by.modules.record.enums.ReplaceHandleFlagEnums;
import com.miguoma.by.modules.record.mapper.RecordBoxCodeMapper;
import com.miguoma.by.modules.record.mapper.RecordBoxCodeReplaceMapper;
import com.miguoma.by.modules.record.query.RecordBoxCodeReplaceQuery;
import com.miguoma.by.modules.record.service.RecordBoxCodeReplaceService;
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
public class RecordBoxCodeReplaceServiceImpl extends BaseServiceImpl<RecordBoxCodeReplaceMapper, RecordBoxCodeReplace>
        implements RecordBoxCodeReplaceService {
    private final RecordBoxCodeMapper recordBoxCodeMapper;

    /**
     * 分页查询
     */
    @Override
    public PageVO<RecordBoxCodeReplace> pageVO(RecordBoxCodeReplaceQuery query) {

        IPage<RecordBoxCodeReplace> resultPage = this.page(getPage(query), buildWrapper(query));

        return PageVO.of(resultPage);
    }

    /**
     * 构建查询条件
     *
     * @param query
     * @return
     */
    private LambdaQueryWrapper<RecordBoxCodeReplace> buildWrapper(RecordBoxCodeReplaceQuery query) {
        LambdaQueryWrapper<RecordBoxCodeReplace> lambdaQuery = Wrappers.lambdaQuery(RecordBoxCodeReplace.class);
        String originalBoxCode = query.getOriginalBoxCode();
        if (StrUtil.isNotBlank(originalBoxCode)) {
            lambdaQuery.eq(RecordBoxCodeReplace::getOriginalBoxCode, originalBoxCode);
        }
        String replaceBoxCode = query.getReplaceBoxCode();
        if (StrUtil.isNotBlank(replaceBoxCode)) {
            lambdaQuery.eq(RecordBoxCodeReplace::getReplaceBoxCode, replaceBoxCode);
        }
        String handleFlag = query.getHandleFlag();
        if (StrUtil.isNotBlank(handleFlag)) {
            lambdaQuery.eq(RecordBoxCodeReplace::getHandleFlag, handleFlag);
        }
        final LocalDateTime submitDatetimeBegin = query.getSubmitDatetimeBegin();
        if (submitDatetimeBegin != null) {
            lambdaQuery.ge(RecordBoxCodeReplace::getSubmitDatetime, submitDatetimeBegin);
        }
        final LocalDateTime submitDatetimeEnd = query.getSubmitDatetimeEnd();
        if (submitDatetimeEnd != null) {
            lambdaQuery.le(RecordBoxCodeReplace::getSubmitDatetime, submitDatetimeEnd);
        }
        lambdaQuery.orderByDesc(RecordBoxCodeReplace::getId);
        return lambdaQuery;
    }

    /**
     * 保存一条记录
     *
     * @param recordBoxCodeReplaceDTO
     */

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOne(RecordBoxCodeReplaceDTO recordBoxCodeReplaceDTO) {
        UserDetail userInfo = SysUserUtil.getUserInfo();
        Long userId = userInfo.getId();
        String username = userInfo.getUsername();
        final LocalDateTime now = LocalDateTimeUtil.now();

        String originalBoxCode = recordBoxCodeReplaceDTO.getOriginalBoxCode();
        String replaceBoxCode = recordBoxCodeReplaceDTO.getReplaceBoxCode();
        final RecordBoxCode replaceBoxCodeEntity = recordBoxCodeMapper.getOneByCode(replaceBoxCode);
        final RecordBoxCode originalBoxCodeEntity = recordBoxCodeMapper.getOneByCode(originalBoxCode);

        RecordBoxCodeReplace recordBoxCodeReplace = new RecordBoxCodeReplace();
        recordBoxCodeReplace.setSubmitUserId(userId);
        recordBoxCodeReplace.setSubmitUsername(username);
        recordBoxCodeReplace.setSubmitDatetime(now);
        recordBoxCodeReplace.setHandleFlag(ReplaceHandleFlagEnums.WAITING.getCode());
        recordBoxCodeReplace.setOriginalBoxCode(originalBoxCode);
        recordBoxCodeReplace.setReplaceBoxCode(replaceBoxCode);

        if(StrUtil.equals(replaceBoxCode, originalBoxCode)){
            recordBoxCodeReplace.setHandleFlag(ReplaceHandleFlagEnums.FAIL.getCode());
            recordBoxCodeReplace.setFailReason("原箱码与替换箱码相同");
            save(recordBoxCodeReplace);
            return;
        }

        if(replaceBoxCodeEntity == null){
            recordBoxCodeReplace.setHandleFlag(ReplaceHandleFlagEnums.FAIL.getCode());
            recordBoxCodeReplace.setFailReason("未找到该替换箱码");
            save(recordBoxCodeReplace);
            return;
        }
        if(originalBoxCodeEntity == null){
            recordBoxCodeReplace.setHandleFlag(ReplaceHandleFlagEnums.FAIL.getCode());
            recordBoxCodeReplace.setFailReason("未找到该原箱码");
            save(recordBoxCodeReplace);
            return;
        }
        final String originalCribCode = originalBoxCodeEntity.getCribCode();
        if(StrUtil.isNotBlank(originalCribCode)){
            recordBoxCodeReplace.setHandleFlag(ReplaceHandleFlagEnums.FAIL.getCode());
            recordBoxCodeReplace.setFailReason("原箱码已组垛");
            save(recordBoxCodeReplace);
            return;
        }
        final String replaceCribCode = replaceBoxCodeEntity.getCribCode();
        if(StrUtil.isBlank(replaceCribCode)){
            recordBoxCodeReplace.setHandleFlag(ReplaceHandleFlagEnums.FAIL.getCode());
            recordBoxCodeReplace.setFailReason("替换箱码未组垛");
            save(recordBoxCodeReplace);
            return;
        }
        LambdaUpdateWrapper<RecordBoxCode> originalLambdaUpdate = Wrappers.lambdaUpdate(RecordBoxCode.class);
        originalLambdaUpdate.eq(RecordBoxCode::getCode, originalBoxCode);
        originalLambdaUpdate.set(RecordBoxCode::getCribCode, replaceCribCode);
        recordBoxCodeMapper.update(null,originalLambdaUpdate);
        LambdaUpdateWrapper<RecordBoxCode> replaceLambdaUpdate = Wrappers.lambdaUpdate(RecordBoxCode.class);
        replaceLambdaUpdate.eq(RecordBoxCode::getCode, replaceBoxCode);
        replaceLambdaUpdate.set(RecordBoxCode::getCribCode, null);
        recordBoxCodeMapper.update(null,replaceLambdaUpdate);
        recordBoxCodeReplace.setHandleFlag(ReplaceHandleFlagEnums.SUCCESS.getCode());
        recordBoxCodeReplace.setHandleDatetime(now);
        
        this.save(recordBoxCodeReplace);
    }


}