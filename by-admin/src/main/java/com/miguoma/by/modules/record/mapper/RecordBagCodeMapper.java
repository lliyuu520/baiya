package com.miguoma.by.modules.record.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.miguoma.by.common.base.mapper.IBaseMapper;
import com.miguoma.by.modules.record.entity.RecordBagCode;
import com.miguoma.by.modules.record.query.RecordBagCodeQuery;
import com.miguoma.by.modules.record.vo.RecordBagCodeVO;

/**
 * 二维码关联持久层接口 提供二维码关联相关的数据库操作
 *
 * @author liliangyu
 */
@Repository
public interface RecordBagCodeMapper extends IBaseMapper<RecordBagCode> {

    /**
     * 分页查询
     * 
     * @param page  分页参数
     * @param query 查询条件
     * @return 分页结果
     */
    IPage<RecordBagCodeVO> pageVO(IPage<RecordBagCode> page, @Param("query") RecordBagCodeQuery query);
}
