package org.javaboy.manual.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

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
    public static final String JAVABOY_QUEUE_NAME = "manual_javaboy_queue_name";
    public static final String JAVABOY_EXCHANGE_NAME = "manual_javaboy_exchange_name";

    @Bean
    Binding msgBinding() {
        return BindingBuilder.bind(msgQueue())
                .to(directExchange())
                .with(JAVABOY_QUEUE_NAME);
    }

    @Bean
    DirectExchange directExchange() {
        return new DirectExchange(JAVABOY_EXCHANGE_NAME, true, false);
    }

    @Bean
    Queue msgQueue() {
        Map<String, Object> args = new HashMap<>();
        //设置消息过期时间，消息过期之后吧，立马就会进入到死信队列中
        //args.put("x-message-ttl", 60*5*1000 + "");
        //指定死信队列的交换机
        args.put("x-dead-letter-exchange", RabbitDlxConfig.DLX_EXCHANGE_NAME);
        //指定死信队列路由的 key
        args.put("x-dead-letter-routing-key", RabbitDlxConfig.DLX_QUEUE_NAME);
        Queue queue = new Queue(JAVABOY_QUEUE_NAME, true, false, false,args);

        return queue;
    }
}
