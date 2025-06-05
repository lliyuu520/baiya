package com.miguoma.by.modules.record.mapper;

import com.miguoma.by.modules.record.vo.RecordBoxCodeVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.miguoma.by.common.base.mapper.IBaseMapper;
import com.miguoma.by.modules.record.entity.RecordBoxCode;
import com.miguoma.by.modules.record.query.RecordBoxCodeQuery;

/**
 * 二维码关联持久层接口 提供二维码关联相关的数据库操作
 *
 * @author liliangyu
 */
@Repository
public interface RecordBoxCodeMapper extends IBaseMapper<RecordBoxCode> {



    
    /**分页查询
     * 
     */
    IPage<RecordBoxCodeVO> pageVO(IPage<RecordBoxCode> page, @Param("query") RecordBoxCodeQuery query);

}
