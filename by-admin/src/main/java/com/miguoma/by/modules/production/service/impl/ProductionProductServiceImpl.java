package com.miguoma.by.modules.production.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.base.service.impl.BaseServiceImpl;
import com.miguoma.by.modules.production.enums.ProductTypeEnum;
import com.miguoma.by.modules.erp.dto.ErpProductDTO;
import com.miguoma.by.modules.production.entity.ProductionProduct;
import com.miguoma.by.modules.production.mapper.ProductionProductCategoryMapper;
import com.miguoma.by.modules.production.mapper.ProductionProductMapper;
import com.miguoma.by.modules.production.query.ProductionProductQuery;
import com.miguoma.by.modules.production.service.ProductionProductService;
import com.miguoma.by.modules.production.vo.ProductionProductVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 产品service实现类
 *
 * @author liliangyu
 */
@Service
@RequiredArgsConstructor
public class ProductionProductServiceImpl extends BaseServiceImpl<ProductionProductMapper, ProductionProduct>
        implements ProductionProductService {

    private final ProductionProductCategoryMapper productionProductCategoryMapper;

    @Override
    public PageVO<ProductionProductVO> pageVO(ProductionProductQuery query) {
        Page<ProductionProduct> page = new Page<>(query.getPage(), query.getLimit());
        LambdaQueryWrapper<ProductionProduct> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(query.getName() != null, ProductionProduct::getName, query.getName());
        wrapper.eq(query.getCode() != null, ProductionProduct::getCode, query.getCode());
        wrapper.eq(query.getCategoryCode() != null, ProductionProduct::getCategoryCode, query.getCategoryCode());
        wrapper.in(ProductionProduct::getProductType, CollUtil.toList(ProductTypeEnum.FINISHED_PRODUCT.getCode(),
                ProductTypeEnum.SEMI_FINISHED_PRODUCT.getCode()));
        wrapper.orderByDesc(ProductionProduct::getCreateTime);
        Page<ProductionProduct> result = this.page(page, wrapper);
        List<ProductionProductVO> records = result.getRecords().stream().map(this::convertToVO)
                .collect(Collectors.toList());
        return new PageVO<>(records, result.getTotal());
    }

    private ProductionProductVO convertToVO(ProductionProduct entity) {
        ProductionProductVO vo = new ProductionProductVO();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }

    /**
     * 同步ERP产品信息
     *
     * @param productList
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void syncProduct(List<ErpProductDTO> productList) {
        productList.forEach(m -> {
            final String code = m.getCode();
            final String name = m.getName();
            final String categoryCode = m.getCategoryCode();
            final String productType = productionProductCategoryMapper.getProductType(categoryCode);
            final String unit = m.getUnit();
            final String oneBoxPackageNumStr = m.getOneBoxPackageNum();
            Integer oneBoxPackageNum = null;
            if (StrUtil.isNotBlank(oneBoxPackageNumStr)) {
                oneBoxPackageNum = Integer.parseInt(oneBoxPackageNumStr);
            }
            final String goodsCode = m.getGoodsCode();
            final String barCode = m.getBarCode();
            final String specification = m.getSpecification();
            final ProductionProduct productionProductDB = baseMapper.getOneByCode(code);
            if (productionProductDB == null) {
                ProductionProduct productionProduct = new ProductionProduct();
                productionProduct.setCode(code);
                productionProduct.setName(name);
                productionProduct.setCategoryCode(categoryCode);
                productionProduct.setProductType(productType);
                productionProduct.setUnit(unit);
                productionProduct.setOneBoxPackageNum(oneBoxPackageNum);
                productionProduct.setGoodsCode(goodsCode);
                productionProduct.setBarCode(barCode);
                productionProduct.setSpecification(specification);
                save(productionProduct);
            } else {
                productionProductDB.setName(name);
                productionProductDB.setCategoryCode(categoryCode);
                productionProductDB.setProductType(productType);
                productionProductDB.setUnit(unit);
                productionProductDB.setOneBoxPackageNum(oneBoxPackageNum);
                productionProductDB.setGoodsCode(goodsCode);
                productionProductDB.setBarCode(barCode);
                productionProductDB.setSpecification(specification);
                updateById(productionProductDB);
            }
        });
    }
}