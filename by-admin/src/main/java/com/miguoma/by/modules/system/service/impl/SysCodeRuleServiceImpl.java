package com.miguoma.by.modules.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.base.service.impl.BaseServiceImpl;
import com.miguoma.by.modules.system.convert.SysCodeRuleConvert;
import com.miguoma.by.modules.system.convert.SysCodeRuleDetailConvert;
import com.miguoma.by.modules.system.dto.SysCodeRuleDTO;
import com.miguoma.by.modules.system.dto.SysCodeRuleDetailDTO;
import com.miguoma.by.modules.system.entity.SysCodeRule;
import com.miguoma.by.modules.system.entity.SysCodeRuleDetail;
import com.miguoma.by.modules.system.enums.RuleTypeEnums;
import com.miguoma.by.modules.system.mapper.SysCodeRuleDetailMapper;
import com.miguoma.by.modules.system.mapper.SysCodeRuleMapper;
import com.miguoma.by.modules.system.query.SysCodeRuleQuery;
import com.miguoma.by.modules.system.service.SysCodeRuleService;
import com.miguoma.by.modules.system.vo.SysCodeRuleVO;

import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;

/**
 * 编码规则服务实现类
 */
@Service
@RequiredArgsConstructor
public class SysCodeRuleServiceImpl extends BaseServiceImpl<SysCodeRuleMapper, SysCodeRule>
        implements SysCodeRuleService {

    private final SysCodeRuleDetailMapper sysCodeRuleDetailMapper;

    /**
     * 分页查询编码规则列表
     *
     * @param query 查询条件
     * @return 分页结果
     */
    @Override
    public PageVO<SysCodeRuleVO> pageVO(SysCodeRuleQuery query) {
        IPage<SysCodeRule> page = page(getPage(query), builderWrapper(query));
        return PageVO.of(SysCodeRuleConvert.INSTANCE.convertToList(page.getRecords()), page.getTotal());
    }

    /**
     * 构建查询条件
     *
     * @param query 查询条件
     * @return 查询包装器
     */
    private LambdaQueryWrapper<SysCodeRule> builderWrapper(SysCodeRuleQuery query) {
        LambdaQueryWrapper<SysCodeRule> wrapper = Wrappers.lambdaQuery();

        // 按名称模糊查询
        final String name = query.getName();
        if (StrUtil.isNotBlank(name)) {
            wrapper.like(SysCodeRule::getName, name);
        }

        // 按编码精确查询
        final String code = query.getCode();
        if (StrUtil.isNotBlank(code)) {
            wrapper.eq(SysCodeRule::getCode, code);
        }

        // 按创建时间降序排序
        wrapper.orderByDesc(SysCodeRule::getId);

        return wrapper;
    }

    /**
     * 新增编码规则
     *
     * @param dto 编码规则信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOne(SysCodeRuleDTO dto) {
        // 参数校验
        if (dto == null) {
            throw new IllegalArgumentException("编码规则信息不能为空");
        }
        if (StrUtil.isBlank(dto.getCode())) {
            throw new IllegalArgumentException("编码规则编码不能为空");
        }
        if (StrUtil.isBlank(dto.getName())) {
            throw new IllegalArgumentException("编码规则名称不能为空");
        }

        // 保存主表
        SysCodeRule sysCodeRule = new SysCodeRule();
        sysCodeRule.setCode(dto.getCode());
        sysCodeRule.setName(dto.getName());
        sysCodeRule.setEnabled(false);
        save(sysCodeRule);

        // 获取规则ID
        final Long codeRuleId = sysCodeRule.getId();

        // 处理所有类型的规则详情
        List<SysCodeRuleDetail> allDetails = new ArrayList<>();

        // 处理箱码规则
        if (dto.getBoxCodeRuleList() != null) {
            allDetails.addAll(convertRuleDetails(dto.getBoxCodeRuleList(), codeRuleId, RuleTypeEnums.BOX.getCode()));
        }

        // 处理袋码规则
        if (dto.getBagCodeRuleList() != null) {
            allDetails.addAll(convertRuleDetails(dto.getBagCodeRuleList(), codeRuleId, RuleTypeEnums.BAG.getCode()));
        }

        // 处理通用码规则
        if (dto.getUniversalCodeRuleList() != null) {
            allDetails.addAll(convertRuleDetails(dto.getUniversalCodeRuleList(), codeRuleId, RuleTypeEnums.UNIVERSAL_CODE.getCode()));
        }

        // 批量保存规则详情
        if (!allDetails.isEmpty()) {
            sysCodeRuleDetailMapper.insertBatch(allDetails);
        }
    }

    /**
     * 转换规则详情
     *
     * @param details  规则详情DTO列表
     * @param ruleId   规则ID
     * @param ruleType 规则类型
     * @return 规则详情实体列表
     */
    private List<SysCodeRuleDetail> convertRuleDetails(List<SysCodeRuleDetailDTO> details, Long ruleId,
            String ruleType) {
        return details.stream()
                .map(detail -> {
                    SysCodeRuleDetail sysCodeRuleDetail = new SysCodeRuleDetail();
                    sysCodeRuleDetail.setRuleType(ruleType);
                    sysCodeRuleDetail.setRuleId(ruleId);
                    sysCodeRuleDetail.setEncodeType(detail.getEncodeType());
                    sysCodeRuleDetail.setIndexBegin(detail.getIndexBegin());
                    sysCodeRuleDetail.setIndexEnd(detail.getIndexEnd());
                    sysCodeRuleDetail.setSourceType(detail.getSourceType());
                    sysCodeRuleDetail.setSourceField(detail.getSourceField());
                    sysCodeRuleDetail.setWeight(detail.getWeight());
                    sysCodeRuleDetail.setConstant(detail.getConstant());
                    return sysCodeRuleDetail;
                })
                .collect(Collectors.toList());
    }

    /**
     * 编辑编码规则
     *
     * @param dto 编码规则信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOne(SysCodeRuleDTO dto) {
        SysCodeRule sysCodeRule = SysCodeRuleConvert.INSTANCE.convertFromDTO(dto);
        updateById(sysCodeRule);
    }

    /**
     * 获取编码规则详情
     *
     * @param id 编码规则ID
     * @return 编码规则详情
     */
    @Override
    public SysCodeRuleVO getOneById(Long id) {
        SysCodeRule sysCodeRule = getById(id);
        SysCodeRuleVO codeRuleVO = SysCodeRuleConvert.INSTANCE.convertToVO(sysCodeRule);
        final List<SysCodeRuleDetail> boxSysCodeRuleDetails = sysCodeRuleDetailMapper
                .selectListByRuleIdSAndType(sysCodeRule.getId(), RuleTypeEnums.BOX.getCode());
        codeRuleVO.setBoxCodeRuleList(SysCodeRuleDetailConvert.INSTANCE.convertList(boxSysCodeRuleDetails));
        final List<SysCodeRuleDetail> bagSysCodeRuleDetails = sysCodeRuleDetailMapper
                .selectListByRuleIdSAndType(sysCodeRule.getId(), RuleTypeEnums.BAG.getCode());
        codeRuleVO.setBagCodeRuleList(SysCodeRuleDetailConvert.INSTANCE.convertList(bagSysCodeRuleDetails));
        final List<SysCodeRuleDetail> qrSysCodeRuleDetails = sysCodeRuleDetailMapper
                .selectListByRuleIdSAndType(sysCodeRule.getId(), RuleTypeEnums.UNIVERSAL_CODE.getCode());
        codeRuleVO.setUniversalCodeRuleList(SysCodeRuleDetailConvert.INSTANCE.convertList(qrSysCodeRuleDetails));

        return codeRuleVO;
    }

    /**
     * 删除编码规则
     *
     * @param id 编码规则ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        removeById(id);
    }

    /**
     * 设置当前编码规则
     *
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setCurrentCodeRule(Long id) {
        // 全局只有一个生效的编码规则
        SysCodeRule sysCodeRuleDB = baseMapper.selectEnabled();
        if (sysCodeRuleDB != null) {
            sysCodeRuleDB.setEnabled(false);
            updateById(sysCodeRuleDB);
        }
        SysCodeRule sysCodeRule = getById(id);
        sysCodeRule.setEnabled(true);
        updateById(sysCodeRule);

    }

    /**
     * 获取当前的编码规则
     *
     * @return
     */
    @Override
    public SysCodeRuleVO getCurrentCodeRule() {
        SysCodeRule sysCodeRule = baseMapper.selectEnabled();
        SysCodeRuleVO codeRuleVO = SysCodeRuleConvert.INSTANCE.convertToVO(sysCodeRule);
        final List<SysCodeRuleDetail> boxSysCodeRuleDetails = sysCodeRuleDetailMapper
                .selectListByRuleIdSAndType(sysCodeRule.getId(), RuleTypeEnums.BOX.getCode());
        codeRuleVO.setBoxCodeRuleList(SysCodeRuleDetailConvert.INSTANCE.convertList(boxSysCodeRuleDetails));
        final List<SysCodeRuleDetail> bagSysCodeRuleDetails = sysCodeRuleDetailMapper
                .selectListByRuleIdSAndType(sysCodeRule.getId(), RuleTypeEnums.BAG.getCode());
        codeRuleVO.setBagCodeRuleList(SysCodeRuleDetailConvert.INSTANCE.convertList(bagSysCodeRuleDetails));
        final List<SysCodeRuleDetail> qrSysCodeRuleDetails = sysCodeRuleDetailMapper.selectListByRuleIdSAndType(
                sysCodeRule.getId(),
                RuleTypeEnums.UNIVERSAL_CODE.getCode());
        codeRuleVO.setUniversalCodeRuleList(SysCodeRuleDetailConvert.INSTANCE.convertList(qrSysCodeRuleDetails));
        return codeRuleVO;
    }
}
