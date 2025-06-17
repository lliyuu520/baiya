package com.miguoma.by.modules.client.dto;

import lombok.Data;

/**
 * 箱码替换记录DTO
 */
@Data
public class RecordBoxCodeReplaceDTO {

    /**
     * 原始箱码
     */
    private String originalBoxCode;

    /**
     * 替换箱码
     */
    private String replaceBoxCode;

    /**
     * 替换原因
     */
    private String replaceReason;
}