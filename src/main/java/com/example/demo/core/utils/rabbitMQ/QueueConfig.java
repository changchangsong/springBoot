package com.example.demo.core.utils.rabbitMQ;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {

    /**
     * 短信发送队列
     *
     * @return
     */
    @Bean
    public Queue sendSmsQueue() {
        return new Queue(QueueNameConfig.SEND_SMS_QUEUE);
    }

    /**
     * 邮箱发送队列
     *
     * @return
     */
    @Bean
    public Queue sendEmailQueue() {
        return new Queue(QueueNameConfig.SEND_EMAIL_QUEUE);
    }

}
