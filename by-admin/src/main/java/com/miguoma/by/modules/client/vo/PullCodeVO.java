package com.miguoma.by.modules.client.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 生成编码VO
 */
@Data
public class PullCodeVO implements Serializable {
    


    /**
     * 二维码类型数据
     */
    private QrCodeTypeData qrCodeTypeData;

    /**
     * 物流码类型数据
     */
    private List<LogisticsTypeData> logisticsTypeDataList;


    /**
     * 二维码类型数据
     */
    @Data
    public static class QrCodeTypeData implements Serializable {

        /**
         * 二维码数据
         */
        private List<String> qrCodeList = new ArrayList<>();

        /**
         * 箱码数据
         */
        private List<String> boxCodeList = new ArrayList<>();

    }

    /**
     * 物流码类型数据
     */
    @Data
    public static class LogisticsTypeData implements Serializable {

        /**
         * 箱码
         */
        private String boxCode ;
        /**
         * 袋码
         */
        private List<String> bagCodeList = new ArrayList<>();


    }


}
