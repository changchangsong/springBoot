package com.example.demo.core.utils.rabbitMQ;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author zhoude
 * @Description 注册成功后发送短信
 * @date 2018/8/9
 */
@Component
@RabbitListener(queues = QueueNameConfig.SEND_SMS_QUEUE)
public class SendSms {

    /**
     * 发送短信
     * @param message
     */
    @RabbitHandler
    public void sendSms(String message) {
        System.err.println(message);
        System.err.println("短信发送成功！");
    }
}