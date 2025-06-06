package com.miguoma.by.modules.production.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.base.service.impl.BaseServiceImpl;
import com.miguoma.by.modules.production.convert.ProductionShiftConvert;
import com.miguoma.by.modules.production.entity.ProductionShift;
import com.miguoma.by.modules.production.mapper.ProductionShiftMapper;
import com.miguoma.by.modules.production.query.ProductionShiftQuery;
import com.miguoma.by.modules.production.service.ProductionShiftService;
import com.miguoma.by.modules.production.vo.ProductionShiftVO;

import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;

/**
 * 班次服务实现类
 * 提供班次相关的业务操作
 *
 * @author liliangyu
 */
@Service
@RequiredArgsConstructor
public class ProductionShiftServiceImpl extends BaseServiceImpl<ProductionShiftMapper, ProductionShift>
        implements ProductionShiftService {

    /**
     * 分页查询班次列表
     *
     * @param query 查询条件
     * @return 分页结果
     */
    @Override
    public PageVO<ProductionShiftVO> pageVO(ProductionShiftQuery query) {
        IPage<ProductionShift> page = page(getPage(query), builderWrapper(query));
        return PageVO.of(ProductionShiftConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
    }

    /**
     * 构建查询条件
     *
     * @param query 查询条件
     * @return 查询包装器
     */
    private LambdaQueryWrapper<ProductionShift> builderWrapper(ProductionShiftQuery query) {
        LambdaQueryWrapper<ProductionShift> wrapper = Wrappers.lambdaQuery();

        // 按名称模糊查询
        final String name = query.getName();
        if (StrUtil.isNotBlank(name)) {
            wrapper.like(ProductionShift::getName, name);
        }

        // 按编码精确查询
        final String code = query.getCode();
        if (StrUtil.isNotBlank(code)) {
            wrapper.eq(ProductionShift::getCode, code);
        }

        // 按创建时间降序排序
        wrapper.orderByDesc(ProductionShift::getId);

        return wrapper;
    }
}
