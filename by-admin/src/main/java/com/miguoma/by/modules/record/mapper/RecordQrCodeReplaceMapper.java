package com.miguoma.by.modules.record.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.miguoma.by.modules.record.entity.RecordQrCodeReplace;

import java.util.List;

/**
 * 二维码替换记录Mapper接口
 */
@Mapper
public interface RecordQrCodeReplaceMapper extends BaseMapper<RecordQrCodeReplace> {

    /**
     * 根据原始二维码查询未处理记录
     * @param originalQrCode
     * @return
     */
    default List<RecordQrCodeReplace> selectNotHandleListByOriginalQrCode(String originalQrCode){
        LambdaQueryWrapper<RecordQrCodeReplace> wrapper= Wrappers.lambdaQuery();
        wrapper.eq(RecordQrCodeReplace::getOriginalQrCode,originalQrCode);
        wrapper.eq(RecordQrCodeReplace::getHandleFlag,false);
        wrapper.orderByAsc(RecordQrCodeReplace::getId);
        return selectList(wrapper);
    }
}