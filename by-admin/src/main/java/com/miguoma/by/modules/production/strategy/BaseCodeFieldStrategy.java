package com.miguoma.by.modules.production.strategy;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.StrUtil;
import com.miguoma.by.common.exception.BaseException;
import com.miguoma.by.common.utils.encode.WebBase36;
import com.miguoma.by.common.utils.encode.WebBase62;
import com.miguoma.by.modules.system.enums.EncodeTypeEnums;

import java.time.LocalDate;

/**
 * 码字段处理策略接口
 * 
 * 一般都是先截取字符串，然后进行编码，最后返回编码后的字符串
 * 用于根据 sourceField 及相关参数生成码片段
 * 
 * 1. 成品生产日期,半成品生产日期 偏移年份
 * 2.成品生产订单编码,半成品生产订单编码 截取字符串 再编码
 * 3.成品生产车间编码,半成品生产车间编码 截取字符串 再编码
 * 4.成品生产产线编码,半成品生产产线编码 截取字符串 再编码
 * 
 * 
 * 
 * 
 * 
 * 
 */
@FunctionalInterface
public interface BaseCodeFieldStrategy {
    /**
     * 处理并返回码片段
     * 
     * @param context 处理上下文（包含所有需要的参数，如订单、产品、规则、index等）
     * @return 码片段字符串
     */
    String apply(CodeFieldContext context);

    /**
     * 获取编码后的字符串
     * 
     * @param str        字符串
     * @param encodeType 编码类型
     * @return 编码后的字符串
     */

    default String getEncodeStr(String str, String encodeType) {
        long i;
        String result = "";
        try {
            i = Long.parseLong(str);
        } catch (NumberFormatException e) {
            throw new BaseException("编码格式错误,请联系管理员");
        }
        if (EncodeTypeEnums.BASE_62.getCode().equals(encodeType)) {
            result = WebBase62.encode(i);
        }
        if (EncodeTypeEnums.BASE_36.getCode().equals(encodeType)) {
            result = WebBase36.encode(i);
        }
        return result;
    }

    /**
     * 截取字符串
     * 
     * @param str        字符串
     * @param indexBegin 开始索引
     * @param indexEnd   结束索引
     * @return 截取后的字符串
     */
    default String getSubStr(String str, Integer indexBegin, Integer indexEnd) {
        return StrUtil.sub(str, indexBegin, indexEnd);
    }

    /**
     * 偏移年份
     * 
     * @param date        日期
     * @param offsetYears 偏移年份
     * @return 偏移后的日期
     */
    default String getOffsetYears(LocalDate date, Integer offsetYears) {
        LocalDate localDate = date.plusYears(offsetYears);
        return LocalDateTimeUtil.format(localDate, DatePattern.PURE_DATE_PATTERN);
    }
}