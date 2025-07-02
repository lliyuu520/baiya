package com.miguoma.by.modules.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.miguoma.by.common.base.entity.BaseEntity;

import com.miguoma.by.modules.system.enums.RandomTypeEnums;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 编码规则
 */
@Data
@TableName(autoResultMap=true)
@EqualsAndHashCode(callSuper = true)
public class SysCodeRule extends BaseEntity {
    /**
     * 规则名称
     */
    private String name;

    /**
     * 规则编码
     */
    private String code;
    /**
     * 二维码url前缀
     */
    private String qrCodeUrlPrefix;

    /**
     * 是否启用
     */
    private Boolean enabled;


    /**
     * 箱码规则
     */
    @TableField(typeHandler = FastjsonTypeHandler.class)
    private List<SysCodeRuleDetail> boxCodeRuleList = new ArrayList<>();

    /**
     * 内箱码规则
     */
    @TableField(typeHandler = FastjsonTypeHandler.class)
    private List<SysCodeRuleDetail> innerBoxCodeRuleList = new ArrayList<>();

    /**
     * 袋码规则
     */
    @TableField(typeHandler = FastjsonTypeHandler.class)
    private List<SysCodeRuleDetail> bagCodeRuleList = new ArrayList<>();

    /**
     * 万用码规则
     */
    @TableField(typeHandler = FastjsonTypeHandler.class)
    private List<SysCodeRuleDetail> universalCodeRuleList = new ArrayList<>();

    /**
     * 规则明细
     */
    @Data
    public static class SysCodeRuleDetail implements Serializable {


        /**
         * 编码类型 {@link com.miguoma.by.common.enums.BaseEncodeEnums}
         */
        private String encodeType;

        /**
         * 来源字段
         */
        private String sourceField;


        /**
         * 开始索引(从0开始)
         */
        private Integer indexBegin;

        /**
         * 结束索引(-1代表最后一个)
         */
        private Integer indexEnd;
        /**
         * 值
         */
        private String constant;

        /**
         * 长度
         */
        private Integer length;

        /**
         * 随机类型 {@link RandomTypeEnums}
         */
        private String randomType;

        /**
         * 偏移年份
         */
        private Integer offsetYears;

        private String specifyBoxNo;

    }

}
