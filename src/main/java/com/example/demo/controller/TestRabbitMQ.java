package com.example.demo.controller;

import com.example.demo.core.utils.rabbitMQ.QueueNameConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;

@RestController
@RequestMapping("rabbitMQ")
@Api(value = "/rabbitMQ", description = "测试rabbitMQ")
public class TestRabbitMQ {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 用户注册，并发送消息给短信、邮箱接口
     * @param userId 用户id
     */
    @RequestMapping(value = "/userId", method = RequestMethod.GET)
    @ApiOperation(value = "用户注册，并发送消息给短信、邮箱接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", paramType = "query", value = "用户ID", required = true, dataType = "String", defaultValue = "1")
    })
    public void register(@RequestParam(name = "userId", required = true) String userId) {
        System.err.println(MessageFormat.format("用户{0}注册成功", userId));
        rabbitTemplate.convertAndSend(QueueNameConfig.SEND_SMS_QUEUE, "短信已发送至：" + userId);
        rabbitTemplate.convertAndSend(QueueNameConfig.SEND_EMAIL_QUEUE, "邮箱已发送至：" + userId);
        System.err.println("完成！");
    }


}
