package com.mitocode.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean("virtualThreadExecutor1")
    public Executor asyncExecutor1() {
        // Configuración tradicional de un ThreadPoolTaskExecutor --//
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(3);
//        executor.setMaxPoolSize(16);
//        executor.setQueueCapacity(100);
//        executor.setThreadNamePrefix("AsyncThread-mito-sales");
//        executor.initialize();
//
//        return executor;

        // Configuración usando Virtual Threads (Java 19+) --//
        return new SimpleAsyncTaskExecutor(Thread.ofVirtual().name("vthread1-", 0).factory());
    }

    @Bean("virtualThreadExecutor2")
    public Executor asyncExecutor2() {
        // Configuración tradicional de un ThreadPoolTaskExecutor --//
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(3);
//        executor.setMaxPoolSize(16);
//        executor.setQueueCapacity(100);
//        executor.setThreadNamePrefix("AsyncThread-mito-sales");
//        executor.initialize();
//
//        return executor;

        // Configuración usando Virtual Threads (Java 19+) --//
        return new SimpleAsyncTaskExecutor(Thread.ofVirtual().name("vthread2-", 0).factory());
    }
}
