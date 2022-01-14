package org.javaboy.confirm.controller;

import org.javaboy.confirm.config.RabbitConfig;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author 江南一点雨
 * @微信公众号 江南一点雨
 * @网站 http://www.itboyhub.com
 * @国际站 http://www.javaboy.org
 * @微信 a_java_boy
 * @GitHub https://github.com/lenve
 * @Gitee https://gitee.com/lenve
 */
@RestController
public class HelloController {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping("/send")
    public void hello() {
        rabbitTemplate.convertAndSend(RabbitConfig.JAVABOY_EXCHANGE_NAME,
                RabbitConfig.JAVABOY_QUEUE_NAME,
                "hello javaboy2!",
                new CorrelationData(UUID.randomUUID().toString()));
    }

    @GetMapping("/sendError")
    public void sendError() {
        // 这个消息将会不能成功到达交换机，因为这个交换机不存在
        rabbitTemplate.convertAndSend("RabbitConfig.JAVABOY_EXCHANGE_NAME",
                RabbitConfig.JAVABOY_QUEUE_NAME,
                "hello javaboy2!",
                new CorrelationData(UUID.randomUUID().toString()));
    }

    @GetMapping("/sendError2")
    public void sendError2() {
        // 这个消息将无法到达队列，因为不存在routingKey对应的队列
        rabbitTemplate.convertAndSend(RabbitConfig.JAVABOY_EXCHANGE_NAME,
                RabbitConfig.JAVABOY_QUEUE_NAME + "1111111",
                "hello javaboy2!",
                new CorrelationData(UUID.randomUUID().toString()));
    }
}
