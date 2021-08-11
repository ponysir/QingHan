package com.daughter.qinghan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class ThreadPoolTaskExecutorConfig {

    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {

        //获取机器本身的cpu
        final int cpu = Runtime.getRuntime().availableProcessors();
        //最大线程池数量
        final int corePoolSize = cpu + 1;
        final int keepAliveTime = 1;
        final int maxQueueNum = 1 << 7;
        final int maximumPoolSize = cpu * 2 + 1;

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maximumPoolSize);
        executor.setQueueCapacity(maxQueueNum);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setKeepAliveSeconds(keepAliveTime);

        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setThreadNamePrefix("provider-pool-thread-");
        executor.initialize();
        return executor;
    }
}
