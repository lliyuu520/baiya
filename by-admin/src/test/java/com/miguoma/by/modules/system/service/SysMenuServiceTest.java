package com.miguoma.by.modules.system.service;

import cn.hutool.core.util.StrUtil;
import com.miguoma.by.modules.production.entity.ProductionProduct;
import com.miguoma.by.modules.production.mapper.ProductionProductCategoryMapper;
import com.miguoma.by.modules.production.service.ProductionProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


/**
 * @author liliangyu
 */
@SpringBootTest(args = {"--server.port=8081"})
public class SysMenuServiceTest {
  
    @Autowired
    private ProductionProductCategoryMapper productionProductCategoryMapper;
   
    @Autowired
    private ProductionProductService productionProductService;


    /**
     *
     */
    @Test
    public void saveOne() {

        final List<ProductionProduct> list = productionProductService.list();
        list.forEach(m->{
            final String categoryCode = m.getCategoryCode();
            final String productType = productionProductCategoryMapper.getProductType(categoryCode);
            if(StrUtil.isNotBlank(productType)) m.setProductType(productType);
        });
        productionProductService.updateBatchById(list);


    }
}