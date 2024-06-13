package com.lycodeing.chat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池配置
 *
 * @author xiaotianyu
 */
@Component
public class ThreadPoolConfig {
    @Bean(name = "customThreadPool")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程数
        executor.setCorePoolSize(15);
        // 最大线程数
        executor.setMaxPoolSize(30);
        // 队列大小
        executor.setQueueCapacity(Integer.MAX_VALUE);
        // 线程名称前缀
        executor.setThreadNamePrefix("CustomThreadPool-");
        // 设置拒绝策略为异常
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        // 初始化线程池
        executor.initialize();
        return executor;
    }
}
