package link.yangxin.rabbitmqmqtt.receiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
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
public class MsgReceiver {

    private static final Logger logger = LoggerFactory.getLogger(MsgReceiver.class);

    @RabbitListener(queues = "mqtt_queue")
    public void handleMsg(String msg) {
        logger.info("msg:{}", msg);
    }
}
