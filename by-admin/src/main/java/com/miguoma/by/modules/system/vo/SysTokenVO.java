package com.miguoma.by.modules.system.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户Token
 *
 * @author lliyuu520 lliyuu520@gmail.com
 */
@Data
@AllArgsConstructor
public class SysTokenVO implements Serializable {

    private String accessToken;
}
