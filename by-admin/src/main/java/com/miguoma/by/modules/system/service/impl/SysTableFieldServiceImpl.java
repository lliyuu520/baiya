package com.miguoma.by.modules.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.miguoma.by.common.base.service.impl.BaseServiceImpl;
import com.miguoma.by.modules.system.dto.SysTableFieldDTO;
import com.miguoma.by.modules.system.entity.SysTableConfig;
import com.miguoma.by.modules.system.entity.SysTableField;
import com.miguoma.by.modules.system.mapper.SysTableConfigMapper;
import com.miguoma.by.modules.system.mapper.SysTableFieldMapper;
import com.miguoma.by.modules.system.service.SysTableFieldService;
import com.miguoma.by.modules.system.vo.SysTableFieldVO;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * 字段配置服务实现
 *
 * @author liliangyu
 */
@Service
@RequiredArgsConstructor
public class SysTableFieldServiceImpl extends BaseServiceImpl<SysTableFieldMapper, SysTableField> implements SysTableFieldService {

    private static final String TABLE_FIELD_QUERY = "SELECT COLUMN_NAME, COMMENTS AS COLUMN_COMMENT FROM USER_COL_COMMENTS WHERE TABLE_NAME = {} ORDER BY COLUMN_ID";
    /**
     * 获取表字段
     */

    private static final List<String> BASE_FIELD_NAME_LIST = CollUtil.toList("id", "create_time", "update_time",
            "creator", "updater", "deleted", "version");
    private final SysTableConfigMapper sysTableConfigMapper;
    /**
     * 数据源配置
     */
    private final DataSourceProperties dataSourceProperties;

    /**
     * 保存字段配置
     *
     * @param sysTableFieldDTO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOne(SysTableFieldDTO sysTableFieldDTO) {
        final Long tableConfigId = sysTableFieldDTO.getTableConfigId();
        final List<SysTableField> sysTableFieldList = sysTableFieldDTO.getSysTableFieldList();
        baseMapper.physicalDeleteByTableConfigId(tableConfigId);
        sysTableFieldList.forEach(tableField -> {
            tableField.setTableConfigId(tableConfigId);
            baseMapper.insert(tableField);
        });
    }

    /**
     * 根据表配置id查询字段配置信息
     *
     * @param tableConfigId
     * @return
     */
    @Override
    public List<String> getOneByTableConfigId(Long tableConfigId) {
        List<SysTableField> sysTableFieldList = baseMapper.listByTableConfigId(tableConfigId);
        return sysTableFieldList.stream().map(SysTableField::getFieldName).toList();
    }

    /**
     * 根据表配置id查询字段配置信息
     *
     * @param tableConfigId 表配置id
     * @return 字段配置信息
     */
    @Override
    @SneakyThrows
    public List<SysTableFieldVO> listByTableConfigId(Long tableConfigId) {
        final SysTableConfig sysTableConfig = sysTableConfigMapper.selectById(tableConfigId);
        final String tableName = sysTableConfig.getTableName();
        final String url = dataSourceProperties.getUrl();
        final String username = dataSourceProperties.getUsername();
        final String password = dataSourceProperties.getPassword();
        Connection connection = DriverManager.getConnection(url, username, password);
        final String tableFieldQueryStr = StrUtil.format(TABLE_FIELD_QUERY, tableName);
        ResultSet resultSet = connection.prepareStatement(tableFieldQueryStr).executeQuery();
        List<SysTableFieldVO> result = new ArrayList<>();
        while (resultSet.next()) {
            final String fieldName = resultSet.getString("column_name");
            // 过滤基础字段
            if (BASE_FIELD_NAME_LIST.contains(fieldName)) {
                continue;
            }
            final String fieldComment = resultSet.getString("column_comment");

            final SysTableFieldVO tableFieldVO = new SysTableFieldVO();
            tableFieldVO.setFieldName(fieldName);
            tableFieldVO.setFieldComment(fieldComment);

            result.add(tableFieldVO);
        }
        connection.close();
        return result;
    }

}
