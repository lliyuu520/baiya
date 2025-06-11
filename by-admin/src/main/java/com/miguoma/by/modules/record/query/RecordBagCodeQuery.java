package com.miguoma.by.modules.record.query;

import cn.hutool.core.util.ArrayUtil;
import com.miguoma.by.common.base.query.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 袋码查询条件
 *
 * @author liliangyu
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RecordBagCodeQuery extends BaseQuery {



    /** 码 */

    private String code;
    /**
     * 拉码类型
     */
    private String pullType;

    /**
     * 成品订单号
     */
    private String finishedOrderNo;
    /**
     * 半成品订单号
     */
    private String semiFinishedOrderNo;

    /**
     * 垛码
     */
    private String boxCode;
    /**
     * 是否上传
     */
    private Boolean uploadFlag;


    /**
     * 上传时间范围
     */
    private LocalDateTime[] uploadDateTimeRange;

    /**
     * 上传时间开始
     */
    private LocalDateTime uploadDateTimeBegin;
    /**
     * 上传时间结束
     */
    private LocalDateTime uploadDateTimeEnd;


    public LocalDateTime getUploadDateTimeBegin() {
        if (ArrayUtil.isNotEmpty(uploadDateTimeRange)) {
            return uploadDateTimeRange[0];
        }
        return null;
    }

    public LocalDateTime getUploadDateTimeEnd() {
        if (ArrayUtil.isNotEmpty(uploadDateTimeRange)) {
            return uploadDateTimeRange[1];
        }
        return null;
    }

    
   
} 
