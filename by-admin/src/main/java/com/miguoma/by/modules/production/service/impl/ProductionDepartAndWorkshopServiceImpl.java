package com.miguoma.by.modules.production.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.base.service.impl.BaseServiceImpl;
import com.miguoma.by.modules.erp.dto.ErpDepartDTO;
import com.miguoma.by.modules.production.convert.ProductionDepartAndWorkshopConvert;
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
        IPage<ProductionDepartAndWorkshop> page = page(getPage(query), builderWrapper(query));
        return PageVO.of(ProductionDepartAndWorkshopConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
    }

    /**
     * 构建查询条件
     *
     * @param query 查询条件
     * @return 查询包装器
     */
    private LambdaQueryWrapper<ProductionDepartAndWorkshop> builderWrapper(ProductionDepartAndWorkshopQuery query) {
        LambdaQueryWrapper<ProductionDepartAndWorkshop> wrapper = Wrappers.lambdaQuery();

        // 按名称模糊查询
        final String name = query.getName();
        if (StrUtil.isNotBlank(name)) {
            wrapper.like(ProductionDepartAndWorkshop::getName, name);
        }

        // 按编码精确查询
        final String code = query.getCode();
        if (StrUtil.isNotBlank(code)) {
            wrapper.eq(ProductionDepartAndWorkshop::getCode, code);
        }

        // 按创建时间降序排序
        wrapper.orderByDesc(ProductionDepartAndWorkshop::getId);

        return wrapper;
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
    public List<String> getDepartCodeListByWorkshopName(String workshopName) {
        LambdaQueryWrapper<ProductionDepartAndWorkshop> wrapper = Wrappers.lambdaQuery();
        wrapper.likeRight(ProductionDepartAndWorkshop::getName, workshopName);
        return list(wrapper).stream().map(ProductionDepartAndWorkshop::getParentCode).toList();
    }
}
