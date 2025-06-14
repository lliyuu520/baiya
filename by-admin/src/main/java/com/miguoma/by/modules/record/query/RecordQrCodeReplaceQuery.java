package com.miguoma.by.modules.record.query;


import cn.hutool.core.util.ArrayUtil;
import com.miguoma.by.common.base.query.BaseQuery;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 二维码替换记录查询对象e
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RecordQrCodeReplaceQuery extends BaseQuery {

    /**
     * 旧二维码
     */
    private String originalQrCode;

    /**
     * 新二维码
     */
    private String replaceQrCode;


    /**
     * 处理状态
     */
    private String handleFlag;

    /**
     * 上传时间范围
     */
    private LocalDateTime[] submitDateTimeRange;

    public LocalDateTime getSubmitDateTimeBegin() {
        if (ArrayUtil.isNotEmpty(submitDateTimeRange)) {
            return submitDateTimeRange[0];
        }
        return null;
    }

    public LocalDateTime getSubmitDateTimeEnd() {
        if (ArrayUtil.isNotEmpty(submitDateTimeRange)) {
            return submitDateTimeRange[1];
        }
        return null;
    }
 
}