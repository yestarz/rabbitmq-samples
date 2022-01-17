package org.javaboy.manual.receiver;

import com.rabbitmq.client.Channel;
import org.javaboy.manual.config.RabbitConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

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
public class MsgReceiver {

    private static final Logger logger = LoggerFactory.getLogger(MsgReceiver.class);

    private AtomicInteger errorCount = new AtomicInteger(1);

    @RabbitListener(queues = RabbitConfig.JAVABOY_QUEUE_NAME)
    public void handleMsg(Message message, Channel channel) {
        //获取消息的一个标记
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            //开始消息的消费
            byte[] body = message.getBody();
            String s = new String(body);
            logger.info("handleMsg,{},deliveryTag:{}", s,deliveryTag);
            int i = 1 / 0;
            //消费完成后，手动ack
            //第一个参数是消息的标记，第二个参数（是否批处理）如果为 false，表示仅仅确认当前消息，如果为 true，表示之前所有的消息都确认消费成功
            channel.basicAck(deliveryTag,false);
        } catch (Exception e) {
            //手动 nack，告诉 mq 这条消息消费失败。第二个参数是否批量处理，第三个参数(requeue)，是否重新进入到队列中
            try {
                // 如果最后一个参数设置为false，就是不让他重新入队列(这条消息消费过了，但是消费失败了)，会进入到死信队列中

                int errorCount = this.errorCount.getAndIncrement();
                logger.info("失败第{}次", errorCount);
                // 失败次数小于10，则重新入队，否则进入到死信队列中
                channel.basicNack(deliveryTag,false,errorCount <= 10);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
