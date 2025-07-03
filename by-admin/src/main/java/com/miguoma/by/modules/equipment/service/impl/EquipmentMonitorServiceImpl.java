package com.miguoma.by.modules.equipment.service.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.base.service.impl.BaseServiceImpl;
import com.miguoma.by.common.exception.BaseException;
import com.miguoma.by.common.utils.ClientContextHolder;
import com.miguoma.by.modules.equipment.dto.EquipmentMonitorDTO;
import com.miguoma.by.modules.equipment.entity.EquipmentClient;
import com.miguoma.by.modules.equipment.entity.EquipmentMonitor;
import com.miguoma.by.modules.equipment.mapper.EquipmentMonitorMapper;
import com.miguoma.by.modules.equipment.query.EquipmentMonitorQuery;
import com.miguoma.by.modules.equipment.service.EquipmentMonitorService;
import com.miguoma.by.modules.production.entity.ProductionOrder;
import com.miguoma.by.modules.production.entity.ProductionProduct;
import com.miguoma.by.modules.production.mapper.ProductionOrderMapper;
import com.miguoma.by.modules.production.mapper.ProductionProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

/**
 * 设备监控服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EquipmentMonitorServiceImpl extends BaseServiceImpl<EquipmentMonitorMapper, EquipmentMonitor>
        implements EquipmentMonitorService {

    private final ProductionOrderMapper productionOrderMapper;
    private final ProductionProductMapper productionProductMapper;

    /**
     * 分页查询设备监控
     *
     * @param query 查询参数
     * @return 设备监控分页数据
     */
    @Override
    public PageVO<EquipmentMonitor> pageVO(EquipmentMonitorQuery query) {
        IPage<EquipmentMonitor> page = this.page(getPage(query), builderWrapper(query));
        return PageVO.of(page);
    }

    /**
     * 构建查询条件
     * 根据查询参数构建MyBatis-Plus的查询条件
     *
     * @param query 查询参数，包含版本号、版本名称等
     * @return 查询条件
     */
    private LambdaQueryWrapper<EquipmentMonitor> builderWrapper(EquipmentMonitorQuery query) {
        LambdaQueryWrapper<EquipmentMonitor> wrapper = Wrappers.lambdaQuery();
        String departNo = query.getDepartNo();
        if (StrUtil.isNotBlank(departNo)) {
            wrapper.eq(EquipmentMonitor::getDepartNo, departNo);
        }
        String workshopNo = query.getWorkshopNo();
        if (StrUtil.isNotBlank(workshopNo)) {
            wrapper.eq(EquipmentMonitor::getWorkshopNo, workshopNo);
        }
        String orderNo = query.getOrderNo();
        if (StrUtil.isNotBlank(orderNo)) {
            wrapper.eq(EquipmentMonitor::getOrderNo, orderNo);
        }
        wrapper.orderByDesc(EquipmentMonitor::getId);

        return wrapper;
    }

    /**
     * 保存设备监控
     *
     * @param dto 设备监控数据传输对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOne(EquipmentMonitorDTO dto) {
        final EquipmentClient equipmentClient = ClientContextHolder.getEquipmentClient();
        String workshopNo = equipmentClient.getWorkshopNo();
        String departNo = equipmentClient.getDepartNo();

        Long finishedProductOrderId = dto.getFinishedProductOrderId();
        ProductionOrder productionOrder = productionOrderMapper.selectById(finishedProductOrderId);
        if (productionOrder == null) {
            throw new BaseException("成品订单不存在:{}", finishedProductOrderId);
        }
        String productCode = productionOrder.getProductCode();
        LocalDate productionDate = productionOrder.getProductionDate();
        ProductionProduct productionProduct = productionProductMapper.getOneByCode(productCode);
        if (productionProduct == null) {
            throw new BaseException("产品不存在:{}", productCode);
        }
        Integer oneBoxPackageNum = productionProduct.getOneBoxPackageNum();
        Integer uploadBoxCodeNum = dto.getUploadBoxCodeNum();

        EquipmentMonitor entity = new EquipmentMonitor();

        entity.setDepartNo(departNo);
        entity.setWorkshopNo(workshopNo);
        entity.setStepName(dto.getStepName());
        entity.setOrderProductionDate(productionDate);
        entity.setOrderNo(productionOrder.getOrderNo());
        entity.setMachineUploadBoxNum(uploadBoxCodeNum);
        entity.setMachineUploadBagNum(uploadBoxCodeNum * oneBoxPackageNum);
        entity.setServerUploadBoxNum(uploadBoxCodeNum);
        entity.setServerUploadBagNum(uploadBoxCodeNum * oneBoxPackageNum);
        entity.setSyncBoxCodeNum(0);
        entity.setStatusDesc("无需同步");
        entity.setInfo("状态校验完成");
        entity.setHandleDateTime(LocalDateTimeUtil.now());
        this.save(entity);
    }

}