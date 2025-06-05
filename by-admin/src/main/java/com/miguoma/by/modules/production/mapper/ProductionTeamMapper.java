package com.miguoma.by.modules.production.mapper;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.miguoma.by.common.base.mapper.IBaseMapper;
import com.miguoma.by.modules.production.entity.ProductionTeam;
import org.springframework.stereotype.Repository;

/**
 * 班组 持久层
 *
 * @author lliyuu520 lliyuu520@gmail.com
 */
@Repository
public interface ProductionTeamMapper extends IBaseMapper<ProductionTeam> {
    /**
     * syncOne
     *
     * @param code 班组编码
     */
    default void syncOne(String code) {
        if(StrUtil.isBlank(code)){
            return;
        }
        LambdaQueryWrapper<ProductionTeam> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ProductionTeam::getCode, code);
        ProductionTeam productionTeamDB = selectOne(wrapper);
        if (productionTeamDB == null) {
            ProductionTeam productionTeam = new ProductionTeam();
            productionTeam.setCode(code);
            productionTeam.setName(code);
            insert(productionTeam);
        }
       
    }
}
