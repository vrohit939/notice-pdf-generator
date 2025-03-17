package com.rv.noticepdfgenerator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
public class AppConfig {

    @Bean
    public Executor pdfTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(20);  // Minimum 20 threads always active
        executor.setMaxPoolSize(100);  // Scale up to 100 during high load
        executor.setQueueCapacity(500); // Pending requests before rejection
        executor.setThreadNamePrefix("PDF-Gen-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy()); // Prevents task rejection
        executor.initialize();
        return executor;
    }


}
