package org.javaboy.consumer.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 江南一点雨
 * @微信公众号 江南一点雨
 * @网站 http://www.itboyhub.com
 * @国际站 http://www.javaboy.org
 * @微信 a_java_boy
 * @GitHub https://github.com/lenve
 * @Gitee https://gitee.com/lenve
 */
@Configuration
public class RabbitConfig {
    public static final String QUEUE_NAME = "javaboy_queue";

    @Bean
    Queue javaboyQueue() {
        //1. 第一个参数是队列的名字
        //2。第二个参数是持久化。消息会从交换机进入到消息队列中，消息队列中的消息是否需要保存呢？如果存下来，rabbitmq如果异常重启了，那么之前的消息也还在，消息会持久化到硬盘中
        //3. 该队列是否具有排他性，有排他性的队列只能被创建其的 Connection 处理。这个队列是由连接创建的，将来只有这个连接去使用，别人使用不了
        //4。如果该队列没有消费者，那么是否自动删除该队列
        return new Queue(QUEUE_NAME, true, false, false);
    }
}
