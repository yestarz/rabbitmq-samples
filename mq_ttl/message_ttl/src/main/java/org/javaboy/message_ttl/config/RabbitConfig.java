package org.javaboy.message_ttl.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
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
    public static final String JAVABOY_MESSSAGE_DELAY_QUEUE_NAME = "javaboy_messsage_delay_queue_name";
    public static final String JAVABOY_MESSSAGE_DELAY_EXCHANGE_NAME = "javaboy_messsage_delay_exchange_name";

    @Bean
    Queue messageDelayQueue() {
        // 排他性exclusive：如果设置为true，那么这个消息队列只有创建这个队列的connection才能访问，其他的connection不能访问这个消息队列，
        // 如果试图在其他连接中访问排他性的队列，那么系统会报一个资源被锁定的错误，
        // 对于排他性队列而言，当连接断开的时候，这个队列将会被系统自动删除，无论这个队列是否声明为持久化队列
        return new Queue(JAVABOY_MESSSAGE_DELAY_QUEUE_NAME, true, false, false);
    }

    @Bean
    DirectExchange messageDelayExchange() {
        return new DirectExchange(JAVABOY_MESSSAGE_DELAY_EXCHANGE_NAME, true, false);
    }

    @Bean
    Binding messageDelayQueueBinding() {
        return BindingBuilder.bind(messageDelayQueue())
                .to(messageDelayExchange())
                .with(JAVABOY_MESSSAGE_DELAY_QUEUE_NAME);
    }
}
