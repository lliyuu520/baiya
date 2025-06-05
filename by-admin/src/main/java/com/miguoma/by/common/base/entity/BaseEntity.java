package com.miguoma.by.common.base.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.Data;

/**
 * Entity基类
 *
 * @author lliyuu520 lliyuu520@gmail.com
 */
@Data
public class BaseEntity {
    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 创建时间
     */
    @JsonIgnore
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JsonIgnore
    private LocalDateTime updateTime;

    /**
     * 创建者
     */
    @JsonIgnore
    private Long creator;

    /**
     * 更新者
     */
    @JsonIgnore
    private Long updater;

    /**
     * 删除标记
     */
    @TableLogic
    @JsonIgnore
    private Integer deleted;
}
