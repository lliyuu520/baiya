package com.miguoma.by.modules.production.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.base.service.impl.BaseServiceImpl;
import com.miguoma.by.modules.production.convert.ProductionProductConvert;
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
    /**
     * 分页查询生产订单列表
     *
     * @param query 查询条件
     * @return 分页结果
     */
    @Override
    public PageVO<ProductionProductVO> pageVO(ProductionProductQuery query) {
        IPage<ProductionProduct> page = page(getPage(query), builderWrapper(query));
        return PageVO.of(ProductionProductConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
    }

    /**
     * 构建查询条件
     *
     * @param query 查询条件
     * @return 查询条件
     */
    private LambdaQueryWrapper<ProductionProduct> builderWrapper(ProductionProductQuery query) {
        LambdaQueryWrapper<ProductionProduct> wrapper = Wrappers.lambdaQuery();
        // 按名称模糊查询
        final String name = query.getName();
        if (StrUtil.isNotBlank(name)) {
            wrapper.like(ProductionProduct::getName, name);
        }
        // 按编码精确查询
        final String code = query.getCode();
        if (StrUtil.isNotBlank(code)) {
            wrapper.eq(ProductionProduct::getCode, code);
        }
        // 按产品类型编码精确查询
        final String categoryCode = query.getCategoryCode();
        if (StrUtil.isNotBlank(categoryCode)) {
            wrapper.eq(ProductionProduct::getCategoryCode, categoryCode);
        }
        final String productType = query.getProductType();
        if(StrUtil.isNotBlank(productType)){
            wrapper.eq(ProductionProduct::getProductType, productType);
        }
        wrapper.in(ProductionProduct::getProductType, CollUtil.toList(ProductTypeEnum.FINISHED_PRODUCT.getCode(),ProductTypeEnum.SEMI_FINISHED_PRODUCT.getCode()));
        wrapper.orderByDesc(ProductionProduct::getId);
        return wrapper;
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