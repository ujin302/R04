package com.example.backend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {
    // Thread 관련 기본 설정
    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 기본 풀 사이즈
        executor.setCorePoolSize(5);
        // 이 스레드 풀이 만들 수 있는 가장 많은 갯수
        executor.setMaxPoolSize(10);
        // 일감 대기 갯수
        executor.setQueueCapacity(500);
        // 로그에 찍히는 이름 ex) Async-1
        executor.setThreadNamePrefix("Async-");
        executor.initialize();
        return executor;
    }

}
