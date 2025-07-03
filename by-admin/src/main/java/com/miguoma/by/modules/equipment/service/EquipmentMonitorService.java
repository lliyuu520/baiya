package com.miguoma.by.modules.equipment.service;

import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.base.service.BaseService;
import com.miguoma.by.modules.equipment.entity.EquipmentMonitor;
import com.miguoma.by.modules.equipment.query.EquipmentMonitorQuery;
import com.miguoma.by.modules.equipment.dto.EquipmentMonitorDTO;

/**
 * 设备监控服务接口
 */
public interface EquipmentMonitorService extends BaseService<EquipmentMonitor> {
    /**
     * 分页查询设备监控
     */
    PageVO<EquipmentMonitor> pageVO(EquipmentMonitorQuery query);

    /**
     * 保存设备监控
     */
    void saveOne(EquipmentMonitorDTO dto);




}