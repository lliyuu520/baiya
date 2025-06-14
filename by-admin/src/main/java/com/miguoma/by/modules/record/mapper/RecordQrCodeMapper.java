package com.miguoma.by.modules.record.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.miguoma.by.common.base.mapper.IBaseMapper;
import com.miguoma.by.modules.record.entity.RecordQrCode;
import com.miguoma.by.modules.record.query.RecordQrCodeQuery;
import com.miguoma.by.modules.record.vo.RecordQrCodeVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 二维码关联持久层接口 提供二维码关联相关的数据库操作
 *
 * @author liliangyu
 */
@Repository
public interface RecordQrCodeMapper extends IBaseMapper<RecordQrCode> {

    /**
     * 分页查询
     * 
     * @param page  分页参数
     * @param query 查询条件
     * @return 分页结果
     */
    IPage<RecordQrCodeVO> pageVO(IPage<RecordQrCode> page, @Param("query") RecordQrCodeQuery query);

    /**
     *  根据二维码查询
     * @param code
     * @return
     */
    RecordQrCodeVO getVOByCode(@Param("code") String code);

    /**
     * 根据二维码获取记录
     * 
     * @param code 二维码
     * @return 记录
     */
    default RecordQrCode getOneByCode(String code) {
        LambdaQueryWrapper<RecordQrCode> lambdaQuery = Wrappers.lambdaQuery(RecordQrCode.class);
        lambdaQuery.eq(RecordQrCode::getCode, code);
        return selectOne(lambdaQuery);
    }

}
