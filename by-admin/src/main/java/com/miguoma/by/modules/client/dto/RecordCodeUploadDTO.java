package com.miguoma.by.modules.client.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liliangyu
 */
@Data
public class RecordCodeUploadDTO implements Serializable {
    
    /**
     * 二维码上传集合
     */
    private RecordQrCodeUploadDTO qrCodeUploadDTO ;


    /**
     * 箱垛上传集合
     */
    private CribCodeUploadDTO cribCodeUploadDTO ;

    /**
     * 箱垛上传集合
     */
    @Data
    public static class CribCodeUploadDTO implements Serializable {
        /**
         * 库存编码
         */
        private String cribCode;
        /**
         * 二维码集合
         */
        private List<String> boxCodeList;
    }


    /**
     * 上传二维码集合
     */
    @Data
    public static class RecordQrCodeUploadDTO implements Serializable {

        /**
         * 箱码
         */
        private String boxCode;
        /**
         * 二维码集合
         */
        private List<String> qrCodeList;
    }


}
