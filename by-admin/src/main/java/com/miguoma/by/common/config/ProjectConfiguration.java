package com.miguoma.by.common.config;

import cn.hutool.core.collection.CollUtil;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 系统参数
 *
 * @author miguoma
 */
@Component
@ConfigurationProperties(prefix = "project")
@Data
public class ProjectConfiguration {
    /**
     * xss配置
     */

    private final Xss xss = new Xss();


    /**
     * xss配置
     */
    @Data
    public static class Xss {
        /**
         * 是否开启xss
         */
        private Boolean enabled = false;
        /**
         * 排除的url
         */
        private List<String> excludeUrls = CollUtil.newArrayList();
    }


}
