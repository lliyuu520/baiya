package com.miguoma.by.modules.record.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.miguoma.by.modules.record.entity.RecordQrCodeReplace;

/**
 * 二维码替换记录Mapper接口
 */
@Mapper
public interface RecordQrCodeReplaceMapper extends BaseMapper<RecordQrCodeReplace> {


}