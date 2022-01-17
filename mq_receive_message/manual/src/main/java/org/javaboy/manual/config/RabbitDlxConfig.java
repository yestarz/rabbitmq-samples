package org.javaboy.manual.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yangxin
 * @date 2022/1/17
 */
@Configuration
public class RabbitDlxConfig {

    public static final String DLX_EXCHANGE_NAME = "yangxin_dlx_exchange";

    public static final String DLX_QUEUE_NAME = "yangxin_dlx_queue";

    @Bean
    public Queue dlxQueue() {
        return new Queue(DLX_QUEUE_NAME, true, false, false);
    }

    @Bean
    public DirectExchange dlxDirectExchange() {
        return new DirectExchange(DLX_EXCHANGE_NAME, true, false);
    }

    @Bean
    public Binding dlxBinding() {
        return BindingBuilder.bind(dlxQueue()).to(dlxDirectExchange()).with(DLX_QUEUE_NAME);
    }

}
