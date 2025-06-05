package com.miguoma.by.modules.production.service.impl;

import java.util.List;

import cn.hutool.core.util.StrUtil;
import com.miguoma.by.modules.erp.dto.ErpProductCategoryDTO;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.base.service.impl.BaseServiceImpl;
import com.miguoma.by.modules.production.convert.ProductionProductCategoryConvert;
import com.miguoma.by.modules.production.dto.ProductionProductCategoryDTO;
import com.miguoma.by.modules.production.entity.ProductionProductCategory;
import com.miguoma.by.modules.production.mapper.ProductionProductCategoryMapper;
import com.miguoma.by.modules.production.query.ProductionProductCategoryQuery;
import com.miguoma.by.modules.production.service.ProductionProductCategoryService;
import com.miguoma.by.modules.production.vo.ProductionProductCategoryVO;

import lombok.RequiredArgsConstructor;

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
     * 新增产品分类
     *
     * @param dto 产品分类信息
     */
    @Override
    public void saveOne(ProductionProductCategoryDTO dto) {
        ProductionProductCategory productCategory = ProductionProductCategoryConvert.INSTANCE.convertFromDTO(dto);
        save(productCategory);
    }

    /**
     * 编辑产品分类
     *
     * @param dto 产品分类信息
     */
    @Override
    public void updateOne(ProductionProductCategoryDTO dto) {
        ProductionProductCategory productCategory = ProductionProductCategoryConvert.INSTANCE.convertFromDTO(dto);
        updateById(productCategory);
    }

    /**
     * 获取产品分类详情
     *
     * @param id 产品分类ID
     * @return 产品分类详情
     */
    @Override
    public ProductionProductCategoryVO getOneById(Long id) {
        ProductionProductCategory productCategory = getById(id);
        return ProductionProductCategoryConvert.INSTANCE.convertToVO(productCategory);
    }

    /**
     * 删除产品分类
     *
     * @param id 产品分类ID
     */
    @Override
    public void deleteById(Long id) {
        removeById(id);
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
        productCategoryList.forEach(m->{
            LambdaQueryWrapper<ProductionProductCategory> wrapper = Wrappers.lambdaQuery();
            final String code = m.getCode();
            final String parentCode = m.getParentCode();
            final String name = m.getName();
            wrapper.eq(ProductionProductCategory::getCode, code);
           final ProductionProductCategory productCategoryDB = getOne(wrapper);
            if(productCategoryDB == null){
                ProductionProductCategory  productCategory = new ProductionProductCategory();
                productCategory.setName(name);
                productCategory.setCode(code);
                productCategory.setParentCode(parentCode);
                save(productCategory);
            }else {
                productCategoryDB.setName(name);
                productCategoryDB.setParentCode(parentCode);
                updateById(productCategoryDB);
            }
        });
        
    }
}