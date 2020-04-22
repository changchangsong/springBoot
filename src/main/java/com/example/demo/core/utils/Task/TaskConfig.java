package com.example.demo.core.utils.Task;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Created by Administrator on 2020/4/7.
 */
@Configuration
public class TaskConfig {
    //线程池中线程数量
    private int corePoolSize =10;
    //最大线程数
    private int maxPoolSize = 200;
    //消息队列
    private int queueCapacity = 10;

    @Bean
    public Executor taskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        //给线程池里的线程取个名字
        executor.setThreadNamePrefix("自定义异步线程");
        executor.initialize();
        return executor;
    }
}
