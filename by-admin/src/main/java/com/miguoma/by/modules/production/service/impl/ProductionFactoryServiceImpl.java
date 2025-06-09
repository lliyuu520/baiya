package com.miguoma.by.modules.production.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.base.service.impl.BaseServiceImpl;
import com.miguoma.by.modules.production.convert.ProductionFactoryConvert;
import com.miguoma.by.modules.production.dto.ProductionFactoryDTO;
import com.miguoma.by.modules.production.entity.ProductionFactory;
import com.miguoma.by.modules.production.mapper.ProductionFactoryMapper;
import com.miguoma.by.modules.production.query.ProductionFactoryQuery;
import com.miguoma.by.modules.production.service.ProductionFactoryService;
import com.miguoma.by.modules.production.vo.ProductionFactoryVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 工厂服务实现类
 * 实现工厂相关的业务操作
 *
 * @author liliangyu
 */
@Service
@RequiredArgsConstructor
public class ProductionFactoryServiceImpl extends BaseServiceImpl<ProductionFactoryMapper, ProductionFactory> implements ProductionFactoryService {

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

    /**
     * 新增工厂
     *
     * @param dto 工厂信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOne(ProductionFactoryDTO dto) {
        ProductionFactory productionFactory = ProductionFactoryConvert.INSTANCE.convertFromDTO(dto);
        save(productionFactory);
    }

    /**
     * 编辑工厂
     *
     * @param dto 工厂信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOne(ProductionFactoryDTO dto) {
        ProductionFactory productionFactory = ProductionFactoryConvert.INSTANCE.convertFromDTO(dto);
        updateById(productionFactory);
    }

    /**
     * 获取工厂详情
     *
     * @param id 工厂ID
     * @return 工厂详情
     */
    @Override
    public ProductionFactoryVO getOneById(Long id) {
        ProductionFactory productionFactory = getById(id);
        return ProductionFactoryConvert.INSTANCE.convertToVO(productionFactory);
    }

    /**
     * 删除工厂
     *
     * @param id 工厂ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        removeById(id);
    }

    /**
     * 校验工厂编码是否存在
     *
     * @param factoryCode
     * @return
     */
    @Override
    public Boolean checkFactoryCode(String factoryCode) {
        LambdaQueryWrapper<ProductionFactory> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ProductionFactory::getCode, factoryCode);
        return count(wrapper) > 0;
    }
}
