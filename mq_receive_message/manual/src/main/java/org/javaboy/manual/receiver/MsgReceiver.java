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
    public void handleMsg(Message message, Channel channel) throws IOException {
        /**
         * prefetchSize：0，单条消息大小限制，0代表不限制
         * prefetchCount：一次性消费的消息数量。会告诉 RabbitMQ 不要同时给一个消费者推送多于 N 个消息，即一旦有 N 个消息还没有 ack，则该 consumer 将 block 掉，直到有消息 ack。
         * global：true、false 是否将上面设置应用于 channel，简单点说，就是上面限制是 channel 级别的还是 consumer 级别。当我们设置为 false 的时候生效，设置为 true 的时候没有了限流功能，因为 channel 级别尚未实现。
         *
         * 注意：prefetchSize 和 global 这两项，rabbitmq 没有实现，暂且不研究。特别注意一点，prefetchCount 在 no_ask=false 的情况下才生效，即在自动应答的情况下这两个值是不生效的。
         */
       // channel.basicQos(0,3,false);
        //获取消息的一个标记
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            Thread.sleep(5000);
            //开始消息的消费
            byte[] body = message.getBody();
            String s = new String(body);
            logger.info("handleMsg,{},deliveryTag:{}", s,deliveryTag);
            //消费完成后，手动ack
            //第一个参数是消息的标记，第二个参数（是否批处理）如果为 false，表示仅仅确认当前消息，如果为 true，表示之前所有的消息都确认消费成功.当该参数为 true 时，则可以一次性确认 delivery_tag 小于等于传入值的所有消息
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
