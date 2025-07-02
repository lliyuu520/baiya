package com.miguoma.by.modules.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.base.service.impl.BaseServiceImpl;
import com.miguoma.by.modules.system.convert.SysCodeRuleConvert;
import com.miguoma.by.modules.system.dto.SysCodeRuleDTO;
import com.miguoma.by.modules.system.entity.SysCodeRule;
import com.miguoma.by.modules.system.mapper.SysCodeRuleMapper;
import com.miguoma.by.modules.system.query.SysCodeRuleQuery;
import com.miguoma.by.modules.system.service.SysCodeRuleService;
import com.miguoma.by.modules.system.vo.SysCodeRuleVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 编码规则服务实现类
 */
@Service
@RequiredArgsConstructor
public class SysCodeRuleServiceImpl extends BaseServiceImpl<SysCodeRuleMapper, SysCodeRule> implements SysCodeRuleService {


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
        wrapper.orderByDesc(SysCodeRule::getEnabled);
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
        final String code = dto.getCode();
        if (StrUtil.isBlank(code)) {
            throw new IllegalArgumentException("编码规则编码不能为空");
        }
        final String name = dto.getName();
        if (StrUtil.isBlank(name)) {
            throw new IllegalArgumentException("编码规则名称不能为空");
        }

        SysCodeRule sysCodeRule = SysCodeRuleConvert.INSTANCE.convertFromDTO(dto);
        sysCodeRule.setEnabled(false);
        save(sysCodeRule);

    }

    /**
     * 编辑编码规则
     *
     * @param dto 编码规则信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOne(SysCodeRuleDTO dto) {
        // 参数校验
        if (dto == null) {
            throw new IllegalArgumentException("编码规则信息不能为空");
        }
        final String code = dto.getCode();
        if (StrUtil.isBlank(code)) {
            throw new IllegalArgumentException("编码规则编码不能为空");
        }
        final String name = dto.getName();
        if (StrUtil.isBlank(name)) {
            throw new IllegalArgumentException("编码规则名称不能为空");
        }

        // 保存主表
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
     * 获取编码规则列表
     *
     * @return
     */
    @Override
    public List<SysCodeRuleVO> listVO() {
        List<SysCodeRule> list = list();
        return SysCodeRuleConvert.INSTANCE.convertToList(list);
    }

}
