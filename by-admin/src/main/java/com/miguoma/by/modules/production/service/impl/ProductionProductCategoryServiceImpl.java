package com.miguoma.by.modules.production.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.base.service.impl.BaseServiceImpl;
import com.miguoma.by.modules.erp.dto.ErpProductCategoryDTO;
import com.miguoma.by.modules.production.convert.ProductionProductCategoryConvert;
import com.miguoma.by.modules.production.entity.ProductionProductCategory;
import com.miguoma.by.modules.production.mapper.ProductionProductCategoryMapper;
import com.miguoma.by.modules.production.query.ProductionProductCategoryQuery;
import com.miguoma.by.modules.production.service.ProductionProductCategoryService;
import com.miguoma.by.modules.production.vo.ProductionProductCategoryVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 产品分类服务实现类
 *
 * @author liliangyu
 */
@Service
@RequiredArgsConstructor
public class ProductionProductCategoryServiceImpl
        extends BaseServiceImpl<ProductionProductCategoryMapper, ProductionProductCategory>
        implements ProductionProductCategoryService {

    /**
     * 分页查询产品分类列表
     *
     * @param query 查询条件
     * @return 分页结果
     */
    @Override
    public PageVO<ProductionProductCategoryVO> pageVO(ProductionProductCategoryQuery query) {
        IPage<ProductionProductCategory> page = page(getPage(query), builderWrapper(query));
        return PageVO.of(ProductionProductCategoryConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
    }

    /**
     * 构建查询条件
     *
     * @param query 查询条件
     * @return 查询条件
     */
    private LambdaQueryWrapper<ProductionProductCategory> builderWrapper(ProductionProductCategoryQuery query) {
        LambdaQueryWrapper<ProductionProductCategory> wrapper = Wrappers.lambdaQuery();
        String name = query.getName();
        if (StrUtil.isNotBlank(name)) {
            wrapper.like(ProductionProductCategory::getName, name);
        }
        String code = query.getCode();
        if (StrUtil.isNotBlank(code)) {
            wrapper.eq(ProductionProductCategory::getCode, code);
        }
        String parentCode = query.getParentCode();
        if (StrUtil.isNotBlank(parentCode)) {
            wrapper.eq(ProductionProductCategory::getParentCode, parentCode);
        }
        wrapper.orderByDesc(ProductionProductCategory::getId);
        return wrapper;
    }

    /**
     * 根据父级编码查询产品分类列表
     *
     * @param parentCode 父级编码
     * @return 产品分类列表
     */
    @Override
    public List<ProductionProductCategory> listByParentCode(String parentCode) {
        LambdaQueryWrapper<ProductionProductCategory> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ProductionProductCategory::getParentCode, parentCode);
        return list(wrapper);
    }

    /**
     * 同步ERP产品分类信息
     *
     * @param productCategoryList
     */
    @Override
    public void syncProductCategory(List<ErpProductCategoryDTO> productCategoryList) {
        productCategoryList.forEach(m -> {
            LambdaQueryWrapper<ProductionProductCategory> wrapper = Wrappers.lambdaQuery();
            final String code = m.getCode();
            final String parentCode = m.getParentCode();
            final String name = m.getName();
            wrapper.eq(ProductionProductCategory::getCode, code);
            final ProductionProductCategory productCategoryDB = getOne(wrapper);
            if (productCategoryDB == null) {
                ProductionProductCategory productCategory = new ProductionProductCategory();
                productCategory.setName(name);
                productCategory.setCode(code);
                productCategory.setParentCode(parentCode);
                save(productCategory);
            } else {
                productCategoryDB.setName(name);
                productCategoryDB.setParentCode(parentCode);
                updateById(productCategoryDB);
            }
        });

    }
}