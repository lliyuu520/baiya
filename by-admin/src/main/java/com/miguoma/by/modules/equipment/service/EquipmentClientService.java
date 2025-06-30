package com.miguoma.by.modules.equipment.service;

import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.base.service.BaseService;
import com.miguoma.by.modules.client.dto.MachineVerifyPasswordDTO;
import com.miguoma.by.modules.equipment.dto.EquipmentClientDTO;
import com.miguoma.by.modules.equipment.entity.EquipmentClient;
import com.miguoma.by.modules.equipment.query.EquipmentClientQuery;
import com.miguoma.by.modules.equipment.vo.EquipmentClientVO;

/**
 * Client管理服务接口
 * 提供Client版本管理的核心业务逻辑，包括版本发布、查询、更新等功能
 *
 * @author liliangyu
 */
public interface EquipmentClientService extends BaseService<EquipmentClient> {

    /**
     * 分页查询Client版本列表
     * 支持按版本号、版本名称等条件进行查询
     *
     * @param query 查询条件，包含版本号、版本名称等
     * @return 分页结果，包含Client版本信息列表
     */
    PageVO<EquipmentClientVO> pageVO(EquipmentClientQuery query);

    /**
     * 新增Client版本
     * 用于发布新的Client版本
     *
     * @param dto Client版本信息，包含版本号、下载地址、版本描述等
     */
    Long saveOne(EquipmentClientDTO dto);

    /**
     * 修改密码
     *
     * @param equipmentClientDTO
     */
    void updatePassword(EquipmentClientDTO equipmentClientDTO);

    /**
     * 验证密码
     *
     * @param machineVerifyPasswordDTO
     */
    Boolean validatePassword(MachineVerifyPasswordDTO machineVerifyPasswordDTO);


}
