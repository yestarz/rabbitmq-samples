package org.javaboy.manual.receiver;

import com.rabbitmq.client.Channel;
import org.javaboy.manual.config.RabbitDlxConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author yangxin
 * @date 2022/1/17
 */
@Component
public class DlxReceiver {

    protected static final Logger logger = LoggerFactory.getLogger(DlxReceiver.class);

    // 这里监听的死信队列
    @RabbitListener(queues = RabbitDlxConfig.DLX_QUEUE_NAME)
    public void handle(Message message, Channel channel) {
        logger.info("消费死信队列里的消息：msg :{},tag:{} ", new String(message.getBody()), message.getMessageProperties().getDeliveryTag());
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
