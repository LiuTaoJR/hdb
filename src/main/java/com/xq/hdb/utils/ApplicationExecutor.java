package com.xq.hdb.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 异步线程池
 *
 * @author zhang
 * @date 2020-12-28
 */
@Configuration
public class ApplicationExecutor {

    /**
     * 自定义线程池
     * <p>
     * 最大同时处理2064个任务,超出将会触发 {@link ThreadPoolExecutor.AbortPolicy}
     * 如果任务任务执行速度非常快，则无视
     *
     * @return ThreadPoolExecutor
     */
    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        return new ThreadPoolExecutor(30,
                50, 60,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(5000),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
    }
}
