package com.miguoma.by.modules.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.miguoma.by.common.base.entity.BaseEntity;
import com.miguoma.by.modules.system.dto.SysCodeRuleDetail;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * 编码规则
 */
@Data
@TableName(autoResultMap = true)
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


}
