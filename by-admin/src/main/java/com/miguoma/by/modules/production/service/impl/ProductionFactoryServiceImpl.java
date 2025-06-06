package com.miguoma.by.modules.production.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.base.service.impl.BaseServiceImpl;
import com.miguoma.by.modules.production.convert.ProductionFactoryConvert;
import com.miguoma.by.modules.production.entity.ProductionFactory;
import com.miguoma.by.modules.production.mapper.ProductionFactoryMapper;
import com.miguoma.by.modules.production.query.ProductionFactoryQuery;
import com.miguoma.by.modules.production.service.ProductionFactoryService;
import com.miguoma.by.modules.production.vo.ProductionFactoryVO;

import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;

/**
 * 工厂服务实现类
 * 实现工厂相关的业务操作
 *
 * @author liliangyu
 */
@Service
@RequiredArgsConstructor
public class ProductionFactoryServiceImpl extends BaseServiceImpl<ProductionFactoryMapper, ProductionFactory>
        implements ProductionFactoryService {

    /**
     * 分页查询工厂列表
     *
     * @param query 查询条件
     * @return 分页结果
     */
    @Override
    public PageVO<ProductionFactoryVO> pageVO(ProductionFactoryQuery query) {
        IPage<ProductionFactory> page = page(getPage(query), builderWrapper(query));
        return PageVO.of(ProductionFactoryConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
    }

    /**
     * 构建查询条件
     *
     * @param query 查询条件
     * @return 查询包装器
     */
    private LambdaQueryWrapper<ProductionFactory> builderWrapper(ProductionFactoryQuery query) {
        LambdaQueryWrapper<ProductionFactory> wrapper = Wrappers.lambdaQuery();

        // 按名称模糊查询
        final String name = query.getName();
        if (StrUtil.isNotBlank(name)) {
            wrapper.like(ProductionFactory::getName, name);
        }

        // 按编码精确查询
        final String code = query.getCode();
        if (StrUtil.isNotBlank(code)) {
            wrapper.eq(ProductionFactory::getCode, code);
        }

        // 按创建时间降序排序
        wrapper.orderByDesc(ProductionFactory::getId);

        return wrapper;
    }
}
