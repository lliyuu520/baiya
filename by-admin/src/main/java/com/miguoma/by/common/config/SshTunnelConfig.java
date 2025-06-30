package com.miguoma.by.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * SSH隧道配置类
 * 用于在local环境下建立SSH隧道，实现本地开发环境连接远程数据库
 * 通过SSH隧道将本地端口转发到远程服务器的指定端口
 *
 * @author miguoma
 * @since 2024-03-21
 */
@ConfigurationProperties(prefix = "ssh.tunnel")
@Data
public class SshTunnelConfig {
    /**
     * 是否启用SSH隧道
     * 默认为false，需要在配置文件中显式设置为true才会启用
     */
    private boolean enabled;
    /**
     * SSH服务器主机地址
     * 例如：192.168.81.117
     */
    private String host;
    /**
     * SSH服务器端口
     * 默认SSH端口为22
     */
    private int port;
    /**
     * SSH登录用户名
     */
    private String username;
    /**
     * SSH登录密码
     */
    private String password;
    /**
     * 本地转发端口
     * 应用程序将通过此端口访问远程服务
     */
    private int localPort;
    /**
     * 远程服务器地址
     * 通常是数据库服务器地址
     */
    private String remoteHost;
    /**
     * 远程服务器端口
     * 通常是数据库服务器端口
     */
    private int remotePort;
}