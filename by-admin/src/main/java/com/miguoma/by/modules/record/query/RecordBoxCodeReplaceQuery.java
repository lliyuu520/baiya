package com.miguoma.by.modules.record.query;

import java.time.LocalDateTime;

import com.miguoma.by.common.base.query.BaseQuery;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 箱码替换记录查询条件
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RecordBoxCodeReplaceQuery extends BaseQuery {

    /**
     * 原始箱码
     */
    private String originalBoxCode;

    /**
     * 替换箱码
     */
    private String replaceBoxCode;

    /**
     * 处理状态
     */
    private String handleFlag;

    /**
     * 提交时间开始
     */
    private LocalDateTime submitDatetimeBegin;

    /**
     * 提交时间结束
     */
    private LocalDateTime submitDatetimeEnd;
}