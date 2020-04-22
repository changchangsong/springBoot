package com.example.demo.core.utils.rabbitMQ;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author zhoude
 * @Description 注册成功后发送邮箱
 * @date 2018/8/9
 */
@Component
@RabbitListener(queues = QueueNameConfig.SEND_EMAIL_QUEUE)
public class SendEmail {

    /**
     * 发送邮箱
     * @param message
     */
    @RabbitHandler
    public void sendEmail(String message){
        System.err.println(message);
        System.err.println("邮箱发送成功！");
    }
}