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

    /**
     * 根据原始二维码查询未处理记录
     * 
     * @param originalQrCode 原始二维码
     * @return 未处理记录列表
     */
    @Select("SELECT * FROM (SELECT a.*, ROWNUM rnum FROM (SELECT * FROM record_qr_code_replace WHERE original_qr_code = #{originalQrCode} AND handle_flag = 0 ORDER BY id) a WHERE ROWNUM <= 10) WHERE rnum > 0")
    List<RecordQrCodeReplace> selectNotHandleListByOriginalQrCode(@Param("originalQrCode") String originalQrCode);

    /**
     * 查询等待处理的记录
     * 
     * @return 等待处理记录列表
     */
    @Select("SELECT * FROM (SELECT a.*, ROWNUM rnum FROM (SELECT * FROM record_qr_code_replace WHERE status = 'WAITING' ORDER BY id) a WHERE ROWNUM <= 10) WHERE rnum > 0")
    List<RecordQrCodeReplace> selectWaitingList();

    /**
     * 分页查询等待处理的记录
     * 
     * @param startRow 开始行号
     * @param endRow   结束行号
     * @return 等待处理记录列表
     */
    @Select("SELECT * FROM (SELECT a.*, ROWNUM rnum FROM (SELECT * FROM record_qr_code_replace WHERE status = 'WAITING' ORDER BY id) a WHERE ROWNUM <= #{endRow}) WHERE rnum >= #{startRow}")
    List<RecordQrCodeReplace> selectWaitingListByPage(@Param("startRow") int startRow, @Param("endRow") int endRow);
}