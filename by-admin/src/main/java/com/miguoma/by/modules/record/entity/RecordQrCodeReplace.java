package com.miguoma.by.modules.record.entity;

import java.time.LocalDateTime;

import com.miguoma.by.common.base.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 二维码替换记录
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RecordQrCodeReplace extends BaseEntity {

    /**
     * 原始二维码
     */
    private String originalQrCode;

    /**
     * 替换二维码
     */
    private String replaceQrCode;

    /**
     * 提交时间
     */
    private LocalDateTime submitDatetime;
    /**
     * 提交用户ID
     */
    private Long submitUserId;
    /**
     * 提交用户名
     */
    private String submitUsername;

    /**
     * 处理时间
     */
    private LocalDateTime handleDatetime;
  
    /**
     * 处理状态
     */
    private Boolean handleFlag;





}
