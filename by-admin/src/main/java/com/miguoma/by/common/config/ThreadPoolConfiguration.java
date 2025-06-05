package com.miguoma.by.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池
 *
 * @author miguoma
 */
@Configuration
public class ThreadPoolConfiguration implements AsyncConfigurer {
    public static final int cpuNum = Runtime.getRuntime().availableProcessors();

    @Bean
    public ThreadPoolExecutor createThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        // 核心线程大小 默认区 CPU 数量
        taskExecutor.setCorePoolSize(cpuNum);
        // 最大线程大小 默认区 CPU * 2 数量
        taskExecutor.setMaxPoolSize(cpuNum * 2);
        // 队列最大容量
        taskExecutor.setQueueCapacity(500);
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        taskExecutor.setAwaitTerminationSeconds(60);
        taskExecutor.setThreadNamePrefix("HAOZI-Thread-");
        taskExecutor.initialize();

        return taskExecutor.getThreadPoolExecutor();
    }


}
