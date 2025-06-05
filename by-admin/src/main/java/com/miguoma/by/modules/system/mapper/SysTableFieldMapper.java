package com.miguoma.by.modules.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.miguoma.by.modules.system.entity.SysTableField;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 字段配置Mapper
 *
 * @author liliangyu
 */
@Repository
public interface SysTableFieldMapper extends BaseMapper<SysTableField> {

    /**
     * 根据tableConfigId物理删除
     */
    void physicalDeleteByTableConfigId(@Param("tableConfigId") Long tableConfigId);

    /**
     * 根据tableConfigId查询字段配置
     */
    default List<SysTableField> listByTableConfigId(Long tableConfigId) {
        final LambdaQueryWrapper<SysTableField> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysTableField::getTableConfigId, tableConfigId);
        return selectList(wrapper);

    }


}
