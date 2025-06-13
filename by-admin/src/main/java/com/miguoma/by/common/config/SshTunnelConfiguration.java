package com.miguoma.by.common.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * SSH隧道服务类
 * 负责建立和管理SSH隧道连接
 *
 * @author miguoma
 * @since 2024-03-21
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
@EnableConfigurationProperties(SshTunnelConfig.class)
public class SshTunnelConfiguration {

    private final SshTunnelConfig sshTunnelConfig;
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
        if (sshTunnelConfig.isEnabled()) {
            try {
                JSch jsch = new JSch();
                session = jsch.getSession(sshTunnelConfig.getUsername(), sshTunnelConfig.getHost(), sshTunnelConfig.getPort());
                session.setPassword(sshTunnelConfig.getPassword());
                session.setConfig("StrictHostKeyChecking", "no");
                session.connect();

                session.setPortForwardingL(sshTunnelConfig.getLocalPort(), sshTunnelConfig.getRemoteHost(), sshTunnelConfig.getRemotePort());
                log.info("SSH tunnel established successfully!!!!!!!!!!!!!!!!!!!!!!!");
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
            log.info("SSH tunnel closed");
        }
    }
}