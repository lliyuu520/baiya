package com.miguoma.by.modules.record.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.miguoma.by.common.base.mapper.IBaseMapper;
import com.miguoma.by.modules.record.entity.RecordBoxCode;
import com.miguoma.by.modules.record.query.RecordBoxCodeQuery;
import com.miguoma.by.modules.record.vo.RecordBoxCodeVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 二维码关联持久层接口 提供二维码关联相关的数据库操作
 *
 * @author liliangyu
 */
@Repository
public interface RecordBoxCodeMapper extends IBaseMapper<RecordBoxCode> {


    /**
     * 分页查询
     */
    IPage<RecordBoxCodeVO> pageVO(IPage<RecordBoxCode> page, @Param("query") RecordBoxCodeQuery query);

    /**
     * 根据二维码查询
     *
     * @param boxCode
     * @return
     */
    default RecordBoxCode getOneByCode(String boxCode) {
        LambdaQueryWrapper<RecordBoxCode> lambdaQuery = Wrappers.lambdaQuery(RecordBoxCode.class);
        lambdaQuery.eq(RecordBoxCode::getCode, boxCode);
        return selectOne(lambdaQuery);

    }

}
