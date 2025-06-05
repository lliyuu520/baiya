package com.miguoma.by.modules.production.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.base.service.impl.BaseServiceImpl;
import com.miguoma.by.modules.production.convert.ProductionShiftConvert;
import com.miguoma.by.modules.production.dto.ProductionShiftDTO;
import com.miguoma.by.modules.production.entity.ProductionShift;
import com.miguoma.by.modules.production.mapper.ProductionShiftMapper;
import com.miguoma.by.modules.production.query.ProductionShiftQuery;
import com.miguoma.by.modules.production.service.ProductionShiftService;
import com.miguoma.by.modules.production.vo.ProductionShiftVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 产线服务实现类
 * 实现产线相关的业务操作
 *
 * @author liliangyu
 */
@Service
@RequiredArgsConstructor
public class ProductionShiftServiceImpl extends BaseServiceImpl<ProductionShiftMapper, ProductionShift>
    implements ProductionShiftService {

    /**
     * 分页查询产线列表
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

    /**
     * 新增产线
     *
     * @param dto 产线信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOne(ProductionShiftDTO dto) {
        ProductionShift productionShift = ProductionShiftConvert.INSTANCE.convertFromDTO(dto);
        save(productionShift);
    }

    /**
     * 编辑产线
     *
     * @param dto 产线信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOne(ProductionShiftDTO dto) {
        ProductionShift productionShift = ProductionShiftConvert.INSTANCE.convertFromDTO(dto);
        updateById(productionShift);
    }

    /**
     * 获取产线详情
     *
     * @param id 产线ID
     * @return 产线详情
     */
    @Override
    public ProductionShiftVO getOneById(Long id) {
        ProductionShift productionShift = getById(id);
        return ProductionShiftConvert.INSTANCE.convertToVO(productionShift);
    }

    /**
     * 删除产线
     *
     * @param id 产线ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        removeById(id);
    }
}
