package com.miguoma.by.modules.equipment.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.base.service.impl.BaseServiceImpl;
import com.miguoma.by.common.exception.BaseException;
import com.miguoma.by.modules.client.dto.MachineVerifyPasswordDTO;
import com.miguoma.by.modules.equipment.convert.EquipmentClientConvert;
import com.miguoma.by.modules.equipment.dto.EquipmentClientDTO;
import com.miguoma.by.modules.equipment.entity.EquipmentClient;
import com.miguoma.by.modules.equipment.mapper.EquipmentClientMapper;
import com.miguoma.by.modules.equipment.query.EquipmentClientQuery;
import com.miguoma.by.modules.equipment.service.EquipmentClientService;
import com.miguoma.by.modules.equipment.vo.EquipmentClientVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * APK管理服务实现类
 * 实现APK版本管理的核心业务逻辑，包括版本发布、查询、更新等功能
 *
 * @author liliangyu
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EquipmentClientServiceImpl extends BaseServiceImpl<EquipmentClientMapper, EquipmentClient> implements EquipmentClientService {


    /**
     * 分页查询APK版本列表
     * 支持按版本号、版本名称等条件进行查询
     *
     * @param query 查询条件，包含版本号、版本名称等
     * @return 分页结果，包含APK版本信息列表
     */
    @Override
    public PageVO<EquipmentClientVO> pageVO(EquipmentClientQuery query) {
        IPage<EquipmentClient> page = page(getPage(query), builderWrapper(query));
        return PageVO.of(EquipmentClientConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
    }

    /**
     * 新增APK版本
     * 用于发布新的APK版本
     *
     * @param dto APK版本信息，包含版本号、下载地址、版本描述等
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOne(EquipmentClientDTO dto) {

        final String macAddress = dto.getMacAddress();
        final String departCode = dto.getDepartCode();
        final String ip = dto.getIp();
        final String machineNo = dto.getMachineNo();
        final String factoryNo = dto.getFactoryNo();
        final String workshopNo = dto.getWorkshopNo();

        final EquipmentClient equipmentClient = baseMapper.selectOneByMacAddress(macAddress);
        if (equipmentClient != null) {
            equipmentClient.setMachineNo(machineNo);
            equipmentClient.setIp(ip);
            equipmentClient.setFactoryNo(factoryNo);
            equipmentClient.setWorkshopNo(workshopNo);
            equipmentClient.setDepartCode(departCode);
            updateById(equipmentClient);
            return;

        }
        EquipmentClient entity = new EquipmentClient();
        entity.setMachineNo(machineNo);
        entity.setIp(ip);
        entity.setFactoryNo(factoryNo);
        entity.setWorkshopNo(workshopNo);
        entity.setDepartCode(departCode);
        entity.setPassword("123456");
        entity.setMacAddress(macAddress);
        save(entity);


    }

    /**
     * 修改密码
     *
     * @param equipmentClientDTO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePassword(EquipmentClientDTO equipmentClientDTO) {
        final Long id = equipmentClientDTO.getId();
        final String password = equipmentClientDTO.getPassword();
        final EquipmentClient byId = getById(id);
        byId.setPassword(password);
        updateById(byId);

    }

    /**
     * 验证密码
     *
     * @param machineVerifyPasswordDTO
     */
    @Override
    public Boolean validatePassword(MachineVerifyPasswordDTO machineVerifyPasswordDTO) {
        final String password0 = machineVerifyPasswordDTO.getPassword();
        final String macAddress = machineVerifyPasswordDTO.getMacAddress();
        final EquipmentClient equipmentClient = baseMapper.selectOneByMacAddress(macAddress);
        if (equipmentClient == null) {
            throw new BaseException("该MAC地址不存在:{}", macAddress);
        }
        final String password = equipmentClient.getPassword();
        if (!StrUtil.equals(password0, password)) {
            log.error("密码错误:{}", password0);
            return false;
        }
        return true;
    }

    /**
     * 根据mac地址查询
     *
     * @param macAddress
     * @return
     */
    @Override
    public EquipmentClient getByMacAddress(String macAddress) {
        return baseMapper.selectOneByMacAddress(macAddress);
    }

    /**
     * 构建查询条件
     * 根据查询参数构建MyBatis-Plus的查询条件
     *
     * @param query 查询参数，包含版本号、版本名称等
     * @return 查询条件
     */
    private LambdaQueryWrapper<EquipmentClient> builderWrapper(EquipmentClientQuery query) {
        LambdaQueryWrapper<EquipmentClient> wrapper = Wrappers.lambdaQuery();
        String factoryNo = query.getFactoryNo();
        if (StrUtil.isNotBlank(factoryNo)) {
            wrapper.eq(EquipmentClient::getFactoryNo, factoryNo);
        }
        String workshopNo = query.getWorkshopNo();
        if (StrUtil.isNotBlank(workshopNo)) {
            wrapper.eq(EquipmentClient::getWorkshopNo, workshopNo);
        }
        String machineNo = query.getMachineNo();
        if (StrUtil.isNotBlank(machineNo)) {
            wrapper.eq(EquipmentClient::getMachineNo, machineNo);
        }
        String ip = query.getIp();
        if (StrUtil.isNotBlank(ip)) {
            wrapper.eq(EquipmentClient::getIp, ip);
        }
        String macAddress = query.getMacAddress();
        if (StrUtil.isNotBlank(macAddress)) {
            wrapper.eq(EquipmentClient::getMacAddress, macAddress);
        }
        wrapper.orderByDesc(EquipmentClient::getId);

        return wrapper;
    }
}