package org.javaboy.client.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
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
    // 消息发送的队列的名字
    public static final String RPC_MSG_QUEUE = "rpc_msg_queue";
    // 消息接收的队列的名字
    public static final String RPC_REPLY_MSG_QUEUE = "rpc_reply_msg_queue";
    // 交换机名字
    public static final String RPC_EXCHANGE = "rpc_exchange";

    @Bean
    Queue msgQueue() {
        return new Queue(RPC_MSG_QUEUE);
    }

    @Bean
    Queue replyQueue() {
        return new Queue(RPC_REPLY_MSG_QUEUE);
    }

    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange(RPC_EXCHANGE);
    }

    @Bean
    Binding msgBinding() {
        return BindingBuilder.bind(msgQueue())
                .to(topicExchange())
                .with(RPC_MSG_QUEUE);
    }

    @Bean
    Binding replyBinding() {
        return BindingBuilder.bind(replyQueue())
                .to(topicExchange())
                .with(RPC_REPLY_MSG_QUEUE);
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        // 响应的队列名字
        rabbitTemplate.setReplyAddress(RPC_REPLY_MSG_QUEUE);
        // 响应的超时时间
        rabbitTemplate.setReplyTimeout(6000);
        return rabbitTemplate;
    }

    @Bean
    SimpleMessageListenerContainer replyContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer(connectionFactory);
        simpleMessageListenerContainer.setQueueNames(RPC_REPLY_MSG_QUEUE);
        simpleMessageListenerContainer.setMessageListener(rabbitTemplate(connectionFactory));
        return simpleMessageListenerContainer;
    }

}
