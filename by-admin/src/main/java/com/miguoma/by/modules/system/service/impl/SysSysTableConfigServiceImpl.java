package com.miguoma.by.modules.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.base.service.impl.BaseServiceImpl;
import com.miguoma.by.modules.system.convert.TableConfigConvert;
import com.miguoma.by.modules.system.convert.TableFieldConvert;
import com.miguoma.by.modules.system.dto.SysTableConfigDTO;
import com.miguoma.by.modules.system.entity.SysTableConfig;
import com.miguoma.by.modules.system.entity.SysTableField;
import com.miguoma.by.modules.system.mapper.SysTableConfigMapper;
import com.miguoma.by.modules.system.mapper.SysTableFieldMapper;
import com.miguoma.by.modules.system.query.SysTableConfigQuery;
import com.miguoma.by.modules.system.service.SysTableConfigService;
import com.miguoma.by.modules.system.vo.SysTableConfigVO;
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
 * 表配置服务实现类
 * 提供数据库表配置相关的业务实现
 *
 * @author liliangyu
 */
@Service
@RequiredArgsConstructor
public class SysSysTableConfigServiceImpl extends BaseServiceImpl<SysTableConfigMapper, SysTableConfig>
        implements SysTableConfigService {
    /**
     * 查询数据库中所有表的SQL语句
     * 用于获取表名和表注释信息
     */
    private static final String TABLE_QUERY = "SELECT TABLE_NAME, COMMENTS AS TABLE_COMMENT FROM USER_TAB_COMMENTS WHERE TABLE_TYPE = 'TABLE'";

    /**
     * 数据源配置属性
     * 用于获取数据库连接信息
     */
    private final DataSourceProperties dataSourceProperties;

    /**
     * 表字段Mapper
     * 用于操作表字段相关的数据库操作
     */
    private final SysTableFieldMapper sysTableFieldMapper;

    /**
     * 查询所有表配置
     * 从数据库中获取所有表的配置信息，排除系统表和已配置的表
     *
     * @return 表配置列表
     */
    @SneakyThrows
    @Override
    public List<SysTableConfig> listAll() {
        final String url = dataSourceProperties.getUrl();
        final String username = dataSourceProperties.getUsername();
        final String password = dataSourceProperties.getPassword();
        Connection connection = DriverManager.getConnection(url, username, password);
        ResultSet resultSet = connection.prepareStatement(TABLE_QUERY).executeQuery();
        List<SysTableConfig> sysTableConfigs = new ArrayList<>();
        final List<String> tableNameList = list().stream().map(SysTableConfig::getTableName).toList();
        while (resultSet.next()) {
            var tableConfig = new SysTableConfig();
            final String tableName = resultSet.getString("TABLE_NAME");
            // 排除sys_和 table_开头的
            if (StrUtil.startWith(tableName, "sys_")) {
                continue;
            }
            // 排除已经存在的表
            if (tableNameList.contains(tableName)) {
                continue;
            }
            tableConfig.setTableName(tableName);
            final String tableComment = resultSet.getString("TABLE_COMMENT");
            tableConfig.setTableComment(tableComment);
            sysTableConfigs.add(tableConfig);
        }
        connection.close();
        return sysTableConfigs;
    }

    /**
     * 保存表配置列表
     * 将表配置DTO列表转换为实体并批量保存
     *
     * @param tableConfigDTOList 表配置DTO列表
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveList(List<SysTableConfigDTO> tableConfigDTOList) {
        final List<SysTableConfig> sysTableConfigList = tableConfigDTOList.stream()
                .map(TableConfigConvert.INSTANCE::convertFromDTO).toList();
        saveBatch(sysTableConfigList);
    }

    /**
     * 分页查询表配置
     * 根据查询条件获取分页的表配置信息
     *
     * @param sysTableConfigQuery 表配置查询条件
     * @return 分页的表配置信息
     */
    @Override
    public PageVO<SysTableConfigVO> pageVO(SysTableConfigQuery sysTableConfigQuery) {
        IPage<SysTableConfig> page = page(getPage(sysTableConfigQuery), buildWrapper(sysTableConfigQuery));
        return PageVO.of(TableConfigConvert.INSTANCE.convertToList(page.getRecords()), page.getTotal());
    }

    /**
     * 构建查询条件
     * 根据查询参数构建MyBatis-Plus查询条件
     *
     * @param sysTableConfigQuery 表配置查询条件
     * @return MyBatis-Plus查询条件
     */
    private LambdaQueryWrapper<SysTableConfig> buildWrapper(SysTableConfigQuery sysTableConfigQuery) {
        LambdaQueryWrapper<SysTableConfig> lambdaQuery = Wrappers.lambdaQuery();
        String tableName = sysTableConfigQuery.getTableName();
        String tableComment = sysTableConfigQuery.getTableComment();
        if (StrUtil.isNotBlank(tableName)) {
            lambdaQuery.like(SysTableConfig::getTableName, tableName);
        }
        if (StrUtil.isNotBlank(tableComment)) {
            lambdaQuery.like(SysTableConfig::getTableComment, tableComment);
        }
        lambdaQuery.orderByDesc(SysTableConfig::getCreateTime);
        return lambdaQuery;
    }

    /**
     * 根据ID获取表配置详情
     * 获取指定ID的表配置信息，并转换为VO对象
     *
     * @param id 表配置ID
     * @return 表配置VO对象
     */
    @Override
    public SysTableConfigVO getOneById(Long id) {
        final SysTableConfig sysTableConfig = getById(id);
        return TableConfigConvert.INSTANCE.convertToVO(sysTableConfig);
    }

    /**
     * 获取所有表结构VO
     * 获取所有表配置信息，并包含每个表的字段信息
     *
     * @return 表配置VO列表
     */
    @Override
    public List<SysTableConfigVO> listAllVO() {
        List<SysTableConfig> list = list();
        List<SysTableConfigVO> convertToList = TableConfigConvert.INSTANCE.convertToList(list);
        convertToList.forEach(tableConfigVO -> {
            List<SysTableField> listByTableConfigId = sysTableFieldMapper.listByTableConfigId(tableConfigVO.getId());
            List<SysTableFieldVO> tableFieldVOList = TableFieldConvert.INSTANCE.convertToList(listByTableConfigId);
            tableConfigVO.setTableFieldVOList(tableFieldVOList);
        });
        return convertToList;
    }
}
