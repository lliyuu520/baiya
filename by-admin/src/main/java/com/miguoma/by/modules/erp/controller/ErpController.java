package com.miguoma.by.modules.erp.controller;


import cn.dev33.satoken.annotation.SaIgnore;
import com.miguoma.by.common.utils.Result;
import com.miguoma.by.modules.erp.annotation.SignatureCheck;
import com.miguoma.by.modules.erp.dto.ErpDepartDTO;
import com.miguoma.by.modules.erp.dto.ErpOrderDTO;
import com.miguoma.by.modules.erp.dto.ErpProductCategoryDTO;
import com.miguoma.by.modules.erp.dto.ErpProductDTO;
import com.miguoma.by.modules.production.service.ProductionDepartAndWorkshopService;
import com.miguoma.by.modules.production.service.ProductionOrderService;
import com.miguoma.by.modules.production.service.ProductionProductCategoryService;
import com.miguoma.by.modules.production.service.ProductionProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ERP接口
 *
 * @author liliangyu
 */
@Slf4j
@RestController
@RequestMapping("/erp")
@RequiredArgsConstructor
@SaIgnore
public class ErpController {


    private final ProductionProductCategoryService productionProductCategoryService;

    private final ProductionProductService productionProductService;
    private final ProductionOrderService productionOrderService;
    private final ProductionDepartAndWorkshopService productionDepartAndWorkshopService;


    /**
     * 批量创建生产部门
     *
     * @param erpDepartDTOList 生产部门信息
     * @return 创建结果
     */
    @SignatureCheck
    @PostMapping("/productionDepart/batchCreate")
    public Result<String> batchCreateProductionDepart(@RequestBody List<ErpDepartDTO> erpDepartDTOList) {
        log.info("接收到批量创建生产部门请求:{}", erpDepartDTOList);
        productionDepartAndWorkshopService.syncProductionDepartAndWorkShop(erpDepartDTOList);
        return Result.ok();
    }


    /**
     * 批量创建产品类别
     *
     * @param erpProductCategoryDTOS 产品类别信息
     * @return 创建结果
     */
    @SignatureCheck
    @PostMapping("/productCategory/batchCreate")
    public Result<String> batchCreateProductCategory(@RequestBody List<ErpProductCategoryDTO> erpProductCategoryDTOS) {
        log.info("接收到批量创建产品类别请求:{}", erpProductCategoryDTOS);
        productionProductCategoryService.syncProductCategory(erpProductCategoryDTOS);
        return Result.ok();
    }


    /**
     * 批量创建产品
     *
     * @param erpProductDTOList 产品信息
     * @return 创建结果
     */
    @SignatureCheck
    @PostMapping("/product/batchCreate")
    public Result<String> batchCreateProduct(@RequestBody List<ErpProductDTO> erpProductDTOList) {
        log.info("接收到批量创建产品请求:{}", erpProductDTOList);
        productionProductService.syncProduct(erpProductDTOList);
        return Result.ok();
    }

    /**
     * 创建订单
     *
     * @param erpOrderDTO 订单信息
     * @return 创建结果
     */
    @SignatureCheck
    @PostMapping("/order/create")
    public Result<String> createOrder(@RequestBody ErpOrderDTO erpOrderDTO) {
        log.info("接收到创建订单请求:{}", erpOrderDTO);
        productionOrderService.syncOrder(erpOrderDTO);
        return Result.ok();
    }


}
