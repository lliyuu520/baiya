package com.miguoma.by.modules.production.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.base.service.impl.BaseServiceImpl;
import com.miguoma.by.modules.production.convert.ProductionTeamConvert;
import com.miguoma.by.modules.production.dto.ProductionTeamDTO;
import com.miguoma.by.modules.production.entity.ProductionTeam;
import com.miguoma.by.modules.production.mapper.ProductionTeamMapper;
import com.miguoma.by.modules.production.query.ProductionTeamQuery;
import com.miguoma.by.modules.production.service.ProductionTeamService;
import com.miguoma.by.modules.production.vo.ProductionTeamVO;
import com.miguoma.by.modules.client.vo.TeamInfo;
import com.miguoma.by.modules.system.entity.SysUser;
import com.miguoma.by.modules.system.enums.UserTypeEnum;
import com.miguoma.by.modules.system.mapper.SysUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 名称替换 服务实现类
 *
 * @author lliyuu520 lliyuu520@gmail.com
 * @since 1.0.0 2025-04-14
 */
@Service
@RequiredArgsConstructor
public class ProductionTeamServiceImpl extends BaseServiceImpl<ProductionTeamMapper, ProductionTeam> implements ProductionTeamService {

    private final SysUserMapper sysUserMapper;

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

    /**
     * 编辑
     *
     * @param dto
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOne(ProductionTeamDTO dto) {
        ProductionTeam productionTeam = ProductionTeamConvert.INSTANCE.convertFromDTO(dto);

        save(productionTeam);

        SysUser sysUser = new SysUser();
        sysUser.setUsername(dto.getCode());
        sysUser.setUserType(UserTypeEnum.CLIENT.getCode());
        sysUserMapper.insert(sysUser);
    }

    /**
     * 新增
     *
     * @param dto
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOne(ProductionTeamDTO dto) {
        ProductionTeam productionTeam = ProductionTeamConvert.INSTANCE.convertFromDTO(dto);

        updateById(productionTeam);
    }

    /**
     * 查看
     *
     * @param id
     * @return
     */
    @Override
    public ProductionTeamVO getOneById(final Long id) {
        final ProductionTeam productionTeam = getById(id);
        return ProductionTeamConvert.INSTANCE.convertToVO(productionTeam);
    }

    /**
     * 删除
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(final Long id) {
        removeById(id);
    }

   
}
