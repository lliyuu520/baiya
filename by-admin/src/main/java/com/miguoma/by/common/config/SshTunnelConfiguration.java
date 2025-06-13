package com.miguoma.by.common.config;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * SSH隧道配置类
 * 用于在local环境下建立SSH隧道，实现本地开发环境连接远程数据库
 * 通过SSH隧道将本地端口转发到远程服务器的指定端口
 *
 * @author miguoma
 * @since 2024-03-21
 */
@Configuration
@ConfigurationProperties(prefix = "ssh.tunnel")
@Data
@Slf4j
public class SshTunnelConfig {

    private final Environment env;
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
    /**
     * SSH会话对象
     */
    private Session session;

    /**
     * 初始化SSH隧道
     * 在Spring容器启动时自动执行
     * 建立SSH连接并设置端口转发
     *
     * @throws RuntimeException 如果SSH隧道建立失败
     */
    @PostConstruct
    public void init() {


        if (enabled) {
            try {
                JSch jsch = new JSch();
                session = jsch.getSession(username, host, port);
                session.setPassword(password);
                session.setConfig("StrictHostKeyChecking", "no");
                session.connect();

                session.setPortForwardingL(localPort, remoteHost, remotePort);
                log.info("SSH tunnel established");
            } catch (Exception e) {
                throw new RuntimeException("Failed to establish SSH tunnel", e);
            }
        }

    }

    /**
     * 关闭SSH隧道
     * 在Spring容器关闭时自动执行
     * 断开SSH连接并清理资源
     */
    @PreDestroy
    public void destroy() {
        if (session != null && session.isConnected()) {
            session.disconnect();
            System.out.println("SSH tunnel closed");
        }
    }


}