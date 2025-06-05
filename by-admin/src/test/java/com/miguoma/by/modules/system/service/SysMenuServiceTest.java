package com.miguoma.by.modules.system.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONUtil;
import com.miguoma.by.modules.erp.dto.ErpOrderDTO;
import com.miguoma.by.modules.erp.dto.ErpProductDTO;
import com.miguoma.by.modules.production.entity.ProductionProduct;
import com.miguoma.by.modules.production.mapper.ProductionProductCategoryMapper;
import com.miguoma.by.modules.production.service.ProductionDepartAndWorkshopService;
import com.miguoma.by.modules.production.service.ProductionOrderService;
import com.miguoma.by.modules.production.service.ProductionProductCategoryService;
import com.miguoma.by.modules.production.service.ProductionProductService;
import io.swagger.v3.core.util.Json;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.awt.*;
import java.io.File;
import java.nio.charset.Charset;
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