package com.miguoma.by.modules.production.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.base.service.impl.BaseServiceImpl;
import com.miguoma.by.modules.production.convert.ProductionTeamConvert;
import com.miguoma.by.modules.production.entity.ProductionTeam;
import com.miguoma.by.modules.production.mapper.ProductionTeamMapper;
import com.miguoma.by.modules.production.query.ProductionTeamQuery;
import com.miguoma.by.modules.production.service.ProductionTeamService;
import com.miguoma.by.modules.production.vo.ProductionTeamVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 班组服务实现类
 *
 * @author liliangyu
 */
@Service
@RequiredArgsConstructor
public class ProductionTeamServiceImpl extends BaseServiceImpl<ProductionTeamMapper, ProductionTeam>
        implements ProductionTeamService {

    /**
     * 分页查询
     *
     * @param query
     * @return
     */
    @Override
    public PageVO<ProductionTeamVO> pageVO(ProductionTeamQuery query) {
        IPage<ProductionTeam> page = page(getPage(query), builderWrapper(query));
        return PageVO.of(ProductionTeamConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
    }

    /**
     * 构建查询条件
     *
     * @param query
     * @return
     */
    private LambdaQueryWrapper<ProductionTeam> builderWrapper(ProductionTeamQuery query) {
        LambdaQueryWrapper<ProductionTeam> wrapper = Wrappers.lambdaQuery();
        final String name = query.getName();
        if (StrUtil.isNotBlank(name)) {
            wrapper.like(ProductionTeam::getName, name);
        }
        final String code = query.getCode();
        if (StrUtil.isNotBlank(code)) {
            wrapper.eq(ProductionTeam::getCode, code);
        }
        wrapper.orderByDesc(ProductionTeam::getId);
        return wrapper;
    }
}
