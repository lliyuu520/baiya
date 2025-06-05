package com.miguoma.by.modules.system.vo;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 角色管理
 *
 * @author lliyuu520 lliyuu520@gmail.com
 */
@Data
public class SysRoleVO implements Serializable {

    private Long id;

    private String name;

    private String remarks;


    private List<Long> menuIdList;


}
