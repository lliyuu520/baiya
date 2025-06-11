package com.miguoma.by.modules.production.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.miguoma.by.common.annotation.SysLogCut;
import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.enums.SysLogModuleEnums;
import com.miguoma.by.common.enums.SysLogTypeEnums;
import com.miguoma.by.common.utils.Result;
import com.miguoma.by.modules.production.dto.ProductionOrderDTO;
import com.miguoma.by.modules.production.entity.ProductionOrder;
import com.miguoma.by.modules.production.query.ProductionOrderQuery;
import com.miguoma.by.modules.production.service.ProductionOrderService;
import com.miguoma.by.modules.production.vo.ProductionOrderVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 生产订单控制器
 * 提供生产订单相关的增删改查接口
 *
 * @author liliangyu
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/production/order")
public class ProductionOrderController {

    private final ProductionOrderService productionOrderService;

    /**
     * 分页查询生产订单列表
     *
     * @param orderQuery 查询条件
     * @return 分页结果
     */
    @GetMapping("/page")
    @SysLogCut(type = SysLogTypeEnums.PAGE, module = SysLogModuleEnums.ORDER)
    @SaCheckPermission(value = "production:order:page")
    public Result<PageVO<ProductionOrderVO>> page(ProductionOrderQuery orderQuery) {
        PageVO<ProductionOrderVO> pageResult = productionOrderService.pageVO(orderQuery);
        return Result.ok(pageResult);
    }

    /**
     * 新增生产订单
     *
     * @param orderDTO 生产订单信息
     * @return 操作结果
     */
    @PostMapping
    @SysLogCut(type = SysLogTypeEnums.INSERT, module = SysLogModuleEnums.ORDER)
    @SaCheckPermission(value = "production:order:save")
    public Result<String> save(@RequestBody ProductionOrderDTO orderDTO) {
        productionOrderService.saveOne(orderDTO);
        return Result.ok();
    }

    /**
     * 编辑生产订单
     *
     * @param orderDTO 生产订单信息
     * @return 操作结果
     */
    @PutMapping
    @SysLogCut(type = SysLogTypeEnums.UPDATE, module = SysLogModuleEnums.ORDER)
    @SaCheckPermission(value = "production:order:update")
    public Result<String> update(@RequestBody ProductionOrderDTO orderDTO) {
        productionOrderService.updateOne(orderDTO);
        return Result.ok();
    }

    /**
     * 删除生产订单
     *
     * @param id 生产订单ID
     * @return 操作结果
     */
    @DeleteMapping("/delete")
    @SysLogCut(type = SysLogTypeEnums.DELETE, module = SysLogModuleEnums.ORDER)
    @SaCheckPermission(value = "production:order:delete")
    public Result<String> delete(Long id) {
        productionOrderService.deleteById(id);
        return Result.ok();
    }

    /**
     * 获取生产订单详情
     *
     * @param id 生产订单ID
     * @return 生产订单详情
     */
    @GetMapping("/info")
    @SysLogCut(type = SysLogTypeEnums.VIEW, module = SysLogModuleEnums.ORDER)
    @SaCheckPermission(value = "production:order:info")
    public Result<ProductionOrderVO> info(Long id) {
        ProductionOrderVO orderVO = productionOrderService.getOneById(id);
        return Result.ok(orderVO);
    }

    /**
     * 获取生产订单列表
     *
     * @return 生产订单列表
     */
    @GetMapping("/list")
    public Result<List<ProductionOrder>> list() {
        List<ProductionOrder> orderList = productionOrderService.list();
        return Result.ok(orderList);
    }

    /**
     * 返工
     *
     * @param id 生产订单ID
     * @return 操作结果
     */
    @PostMapping("/rework")
    @SysLogCut(type = SysLogTypeEnums.REWORK, module = SysLogModuleEnums.ORDER)
    @SaCheckPermission(value = "production:order:rework")
    public Result<String> rework(Long id) {
        productionOrderService.rework(id);
        return Result.ok();
    }

    /**
     * 取消返工
     *
     * @param id 生产订单ID
     * @return 操作结果
     */
    @PostMapping("/cancelRework")
    @SysLogCut(type = SysLogTypeEnums.CANCEL_REWORK, module = SysLogModuleEnums.ORDER)
    @SaCheckPermission(value = "production:order:cancelRework")
    public Result<String> cancelRework(Long id) {
        productionOrderService.cancelRework(id);
        return Result.ok();
    }
}