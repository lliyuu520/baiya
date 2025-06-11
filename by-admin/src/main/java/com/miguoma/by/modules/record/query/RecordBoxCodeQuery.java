package com.miguoma.by.modules.record.query;

import java.time.LocalDateTime;

import com.miguoma.by.common.base.query.BaseQuery;

import cn.hutool.core.util.ArrayUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 件码查询条件
 * 
 *
 * @author liliangyu
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RecordBoxCodeQuery extends BaseQuery {
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
    private String cribCode;
    /**
     * 是否上传
     */
    private Boolean uploadFlag;
    /**
     * 是否组垛
     */
    private Boolean cribFlag;

    /**
     * 上传时间范围
     */
    private LocalDateTime[] uploadDateTimeRange;
    /**
     * 组垛时间范围
     */
    private LocalDateTime[] cribDateTimeRange;
    /**
     * 上传时间开始
     */
    private LocalDateTime uploadDateTimeBegin;
    /**
     * 上传时间结束
     */
    private LocalDateTime uploadDateTimeEnd;
    /**
     * 组垛时间开始
     */
    private LocalDateTime cribDateTimeBegin;
    /**
     * 组垛时间结束
     */
    private LocalDateTime cribDateTimeEnd;

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

    public LocalDateTime getCribDateTimeBegin() {
        if (ArrayUtil.isNotEmpty(cribDateTimeRange)) {
            return cribDateTimeRange[0];
        }
        return null;
    }

    public LocalDateTime getCribDateTimeEnd() {
        if (ArrayUtil.isNotEmpty(cribDateTimeRange)) {
            return cribDateTimeRange[1];
        }
        return null;
    }

}
