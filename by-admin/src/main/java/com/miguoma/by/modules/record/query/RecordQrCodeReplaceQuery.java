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
    private LocalDateTime[] submitDatetimeRange;

    /**
     * 上传时间开始
     */
    private LocalDateTime submitDatetimeBegin;

    /**
     * 上传时间结束
     */
    private LocalDateTime submitDatetimeEnd;


    public LocalDateTime getSubmitDatetimeBegin() {
        if (ArrayUtil.isNotEmpty(submitDatetimeRange)) {
            return submitDatetimeRange[0];
        }
        return null;
    }

    public LocalDateTime getSubmitDatetimeEnd() {
        if (ArrayUtil.isNotEmpty(submitDatetimeRange)) {
            return submitDatetimeRange[1];
        }
        return null;
    }

}