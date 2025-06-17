package com.miguoma.by.modules.equipment.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.miguoma.by.common.base.mapper.IBaseMapper;
import com.miguoma.by.modules.equipment.entity.EquipmentClient;
import org.springframework.stereotype.Repository;

/**
 * Client管理持久层接口
 * 提供Client版本管理的数据库操作，包括版本信息的增删改查等功能
 *
 * @author liliangyu
 */
@Repository
public interface EquipmentClientMapper extends IBaseMapper<EquipmentClient> {
    /**
     * 根据MAC地址查询Client信息
     * @param macAddress
     * @return
     */
    default EquipmentClient selectOneByMacAddress(String macAddress){
        final LambdaQueryWrapper<EquipmentClient> lambdaQuery = Wrappers.lambdaQuery(EquipmentClient.class);
        lambdaQuery.eq(EquipmentClient::getMacAddress, macAddress);
        return selectOne(lambdaQuery);
    }

}