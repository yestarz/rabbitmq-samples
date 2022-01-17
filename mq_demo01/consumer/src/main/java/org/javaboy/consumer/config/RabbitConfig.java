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
        //2。第二个参数是队列本身的元数据持久化。消息是否持久化根据消息的配置来，要把消息的DeliveryMode设置为2.只不过Spring的RabbitTemplate封装的发送消息逻辑，默认将DeliveryMode设置为2了，所以无需额外设置
        //3. 该队列是否具有排他性，有排他性的队列只能被创建其的 Connection 处理。这个队列是由连接创建的，将来只有这个连接去使用，别人使用不了
        //4。如果该队列没有消费者，那么是否自动删除该队列
        return new Queue(QUEUE_NAME, true, false, false);
    }
}
