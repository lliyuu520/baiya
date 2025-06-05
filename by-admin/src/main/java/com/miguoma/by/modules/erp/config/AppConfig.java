package com.miguoma.by.modules.erp.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 应用配置
 *
 * @author AI Assistant
 */
@Data
@Component
@ConfigurationProperties(prefix = "signature")
public class AppConfig {

    /**
     * 是否启用签名验证
     */
    private boolean enabled = true;

    /**
     * 应用配置映射 app_id -> app_secret
     */
    private Map<String, String> apps;

    /**
     * 获取应用密钥
     *
     * @param appId 应用ID
     * @return 应用密钥
     */
    public String getAppSecret(String appId) {
        return apps != null ? apps.get(appId) : null;
    }

    /**
     * 验证应用ID是否有效
     *
     * @param appId 应用ID
     * @return 是否有效
     */
    public boolean isValidAppId(String appId) {
        return apps != null && apps.containsKey(appId);
    }
}