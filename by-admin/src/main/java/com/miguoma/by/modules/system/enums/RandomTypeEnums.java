package com.miguoma.by.modules.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 随机字符类型
 *
 * @author liliangyu
 */
@Getter
@AllArgsConstructor
public enum RandomTypeEnums  {
    /**
     * 数字
     */
    NUMBER("NUMBER", "数字"),
    /**
     * 大写字母
     */
    UPPER_CASE_LETTER("UPPER_CASE_LETTER", "大写字母"),

    /**
     * 小写字母
     */
    LOWER_CASE_LETTER("LOWER_CASE_LETTER", "小写字母"),

    /**
     * 数字+大写字母
     */
    NUMBER_UPPER_CASE_LETTER("NUMBER_UPPER_CASE_LETTER", "数字+大写字母"),

    /**
     * 数字+小写字母
     */
    NUMBER_LOWER_CASE_LETTER("NUMBER_LOWER_CASE_LETTER", "数字+小写字母"),

    /**
     * 数字+大写字母+小写字母
     */
    NUMBER_UPPER_CASE_LETTER_LOWER_CASE_LETTER("NUMBER_UPPER_CASE_LETTER_LOWER_CASE_LETTER", "数字+大写字母+小写字母"),



    ;
    /**
     * 编码
     */
    private final String code;
    /**
     * 描述
     */
    private final String desc;
}
