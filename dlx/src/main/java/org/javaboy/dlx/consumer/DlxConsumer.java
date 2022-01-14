package org.javaboy.dlx.consumer;

import org.javaboy.dlx.config.RabbitDlxConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author 江南一点雨
 * @微信公众号 江南一点雨
 * @网站 http://www.itboyhub.com
 * @国际站 http://www.javaboy.org
 * @微信 a_java_boy
 * @GitHub https://github.com/lenve
 * @Gitee https://gitee.com/lenve
 */
@Component
public class DlxConsumer {
    // 这里监听的死信队列
    @RabbitListener(queues = RabbitDlxConfig.DLX_QUEUE_NAME)
    public void handle(String msg) {
        System.out.println("消费死信队列里的消息：msg = " + msg);
    }

}
