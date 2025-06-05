package com.miguoma.by.modules.production.mapper;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.miguoma.by.common.base.mapper.IBaseMapper;
import com.miguoma.by.modules.production.entity.ProductionShift;
import org.springframework.stereotype.Repository;

/**
 * 产线持久层接口
 * 提供产线相关的数据库操作
 *
 * @author liliangyu
 */
@Repository
public interface ProductionShiftMapper extends IBaseMapper<ProductionShift> {
    /**
     * syncOne
     *
     * @param code
     */
    default void syncOne(String code) {
        if(StrUtil.isBlank(code)){
            return;
        }
        LambdaQueryWrapper<ProductionShift> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(ProductionShift::getCode, code);
        ProductionShift productionShiftDB = selectOne(queryWrapper);
        if (productionShiftDB == null) {
            ProductionShift productionShift = new ProductionShift();
            productionShift.setCode(code);
            productionShift.setName(code);
            insert(productionShift);
        }

    }

}
