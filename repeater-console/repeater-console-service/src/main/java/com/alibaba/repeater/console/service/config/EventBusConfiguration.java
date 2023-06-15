package com.alibaba.repeater.console.service.config;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class EventBusConfiguration {
    @Bean
    public EventBus eventBus() {
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(30);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 20, 30, TimeUnit.SECONDS, workQueue);
        return new AsyncEventBus(executor);
    }
}
