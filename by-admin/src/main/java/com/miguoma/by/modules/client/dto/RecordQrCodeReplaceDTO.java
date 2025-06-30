package com.miguoma.by.modules.client.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RecordQrCodeReplaceDTO  implements Serializable{

    /**
     * 原始二维码
     */
    private String originalQrCode;

    /**
     * 替换二维码
     */
    private String replaceQrCode;
    /**
     * 替换原因
     */
    private String replaceReason;

}
