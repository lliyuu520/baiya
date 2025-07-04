package com.miguoma.by.modules.production.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.base.service.impl.BaseServiceImpl;
import com.miguoma.by.modules.erp.dto.ErpDepartDTO;
import com.miguoma.by.modules.production.dto.ProductionDepartAndWorkshopDTO;
import com.miguoma.by.modules.production.entity.ProductionDepartAndWorkshop;
import com.miguoma.by.modules.production.mapper.ProductionDepartAndWorkshopMapper;
import com.miguoma.by.modules.production.query.ProductionDepartAndWorkshopQuery;
import com.miguoma.by.modules.production.service.ProductionDepartAndWorkshopService;
import com.miguoma.by.modules.production.vo.ProductionDepartAndWorkshopVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 生产部门&车间服务实现类
 * 实现生产部门&车间相关的业务操作
 *
 * @author liliangyu
 */
@Service
@RequiredArgsConstructor
public class ProductionDepartAndWorkshopServiceImpl
        extends BaseServiceImpl<ProductionDepartAndWorkshopMapper, ProductionDepartAndWorkshop>
        implements ProductionDepartAndWorkshopService {

    /**
     * 分页查询生产部门&车间列表
     *
     * @param query 查询条件
     * @return 分页结果
     */
    @Override
    public PageVO<ProductionDepartAndWorkshopVO> pageVO(ProductionDepartAndWorkshopQuery query) {
        IPage<ProductionDepartAndWorkshopVO> pageVO = baseMapper.pageVO(getPage(query), query);
        return PageVO.of(pageVO);
    }




    /**
     * 同步生产部门和车间信息
     *
     * @param erpDepartDTOList
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void syncProductionDepartAndWorkShop(List<ErpDepartDTO> erpDepartDTOList) {
        erpDepartDTOList.forEach(erpDepartDTO -> {
            // 检查是否存在相同的code
            final String code = erpDepartDTO.getCode();
            final String parentCode = erpDepartDTO.getParentCode();
            final String name = erpDepartDTO.getName();
            LambdaQueryWrapper<ProductionDepartAndWorkshop> queryWrapper = Wrappers.lambdaQuery();
            queryWrapper.eq(ProductionDepartAndWorkshop::getCode, code);
            ProductionDepartAndWorkshop productionDepartAndWorkshopDB = getOne(queryWrapper);
            if (productionDepartAndWorkshopDB == null) {
                // 不存在则新增
                ProductionDepartAndWorkshop productionDepartAndWorkshop = new ProductionDepartAndWorkshop();
                productionDepartAndWorkshop.setName(erpDepartDTO.getName());
                productionDepartAndWorkshop.setCode(code);
                productionDepartAndWorkshop.setParentCode(parentCode);
                save(productionDepartAndWorkshop);
            } else {
                // 存在则更新
                productionDepartAndWorkshopDB.setName(name);
                productionDepartAndWorkshopDB.setParentCode(parentCode);
                updateById(productionDepartAndWorkshopDB);
            }
        });
    }

    /**
     * 配置编码规则
     *
     * @param productionDepartAndWorkshopDTO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void configCodeRule(ProductionDepartAndWorkshopDTO productionDepartAndWorkshopDTO) {
        Long id = productionDepartAndWorkshopDTO.getId();
        Long codeRuleId = productionDepartAndWorkshopDTO.getCodeRuleId();
        ProductionDepartAndWorkshop productionDepartAndWorkshop = getById(id);
        productionDepartAndWorkshop.setCodeRuleId(codeRuleId);
        updateById(productionDepartAndWorkshop);
    }

    /**
     * 配置别名
     *
     * @param productionDepartAndWorkshopDTO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void configAlias(ProductionDepartAndWorkshopDTO productionDepartAndWorkshopDTO) {
        Long id = productionDepartAndWorkshopDTO.getId();
        String alias = productionDepartAndWorkshopDTO.getAlias();
        ProductionDepartAndWorkshop productionDepartAndWorkshop = getById(id);
        productionDepartAndWorkshop.setAlias(alias);
        updateById(productionDepartAndWorkshop);
    }

    /**
     * 校验产线编码
     *
     * @param workshopName
     * @return
     */
    @Override
    public Boolean checkWorkshopName(String workshopName) {
        LambdaQueryWrapper<ProductionDepartAndWorkshop> wrapper = Wrappers.lambdaQuery();
        wrapper.likeRight(ProductionDepartAndWorkshop::getName, workshopName);
        return count(wrapper) > 0;
    }

    /**
     * 根据产线名称获取车间编码列表
     *
     * @param workshopName
     * @return
     */
    @Override
    public List<String> getDepartNameListByWorkshopName(String workshopName) {
        LambdaQueryWrapper<ProductionDepartAndWorkshop> wrapper = Wrappers.lambdaQuery();
        wrapper.likeRight(ProductionDepartAndWorkshop::getName, workshopName);
        final List<String> parentCodeList = list(wrapper).stream().map(ProductionDepartAndWorkshop::getParentCode).distinct().toList();
        return parentCodeList.stream().map(m -> getOneByCode(m).getName()).toList();
    }

    /**
     * 根据编码查询部门&车间信息
     *
     * @param code
     * @return
     */
    @Override
    public ProductionDepartAndWorkshop getOneByCode(String code) {
       return baseMapper.getOneByCode(code);
    }
}
