package org.javaboy.publisher;

import org.javaboy.publisher.config.FanoutConfig;
import org.javaboy.publisher.config.HeaderConfig;
import org.javaboy.publisher.config.RabbitConfig;
import org.javaboy.publisher.config.TopicConfig;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;

@SpringBootTest
class PublisherApplicationTests {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    void contextLoads() {
        // 1. 交换机名字
        // 2. RoutingKey的名字，也是队列的名字
        MessageProperties messageProperties = new MessageProperties();
        Message message = new Message("hahah".getBytes(StandardCharsets.UTF_8), messageProperties);
        rabbitTemplate.convertAndSend(RabbitConfig.DIRECT_EXCHANGE_NAME, RabbitConfig.DIRECT_QUEUE_NAME, "这条消息发给队列1");
        rabbitTemplate.convertAndSend(RabbitConfig.DIRECT_EXCHANGE_NAME, RabbitConfig.DIRECT_QUEUE_NAME2, "这条消息发给队列2");
    }

    @Test
    void test01() {
        rabbitTemplate.convertAndSend(FanoutConfig.FANOUT_EXCHANGE_NAME, null, "hello fanout!");
    }

    @Test
    void test02() {
        rabbitTemplate.convertAndSend(TopicConfig.TOPIC_EXCHANGE_NAME, "huawei.phone.news", "华为手机新闻");
       // rabbitTemplate.convertAndSend(TopicConfig.TOPIC_EXCHANGE_NAME, "xiaomi.phone.news", "小米手机新闻");
    }

    @Test
    void test03() {
        Message nameMsg = MessageBuilder.withBody("hello zhangsan".getBytes()).setHeader("name", "aaa").build();
        rabbitTemplate.send(HeaderConfig.HEADER_EXCHANGE_NAME, null, nameMsg);
        Message ageMsg = MessageBuilder.withBody("hello lisi 99".getBytes()).setHeader("age", 99).build();
        rabbitTemplate.send(HeaderConfig.HEADER_EXCHANGE_NAME, null, ageMsg);
    }
}
