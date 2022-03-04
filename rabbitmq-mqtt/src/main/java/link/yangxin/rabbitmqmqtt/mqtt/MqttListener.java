package link.yangxin.rabbitmqmqtt.mqtt;

import link.yangxin.rabbitmqmqtt.mqtt.message.MqttTopicRouter;
import link.yangxin.rabbitmqmqtt.mqtt.pojo.MqttMessage;
import lombok.Data;
import org.fusesource.hawtbuf.Buffer;
import org.fusesource.hawtbuf.UTF8Buffer;
import org.fusesource.mqtt.client.Listener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yangxin
 * @date 2021/5/14
 */
@Data
public class MqttListener implements Listener {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private MqttTopicRouter router;

    @Override
    public void onConnected() {
        logger.debug("mqtt消息服务器连接成功");
    }

    @Override
    public void onDisconnected() {
        logger.debug("mqtt消息服务器断开连接");
    }

    @Override
    public void onPublish(UTF8Buffer topic, Buffer message, Runnable callback) {
        try {
            String topicName = topic.toString();
            String messageContent = message.utf8().toString();
            logger.debug("收到topic:{}消息为：{}", topicName, messageContent);
            if (this.router != null) {
                this.router.route(new MqttMessage(topicName, messageContent));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            callback.run();
        }
    }

    @Override
    public void onFailure(Throwable throwable) {
        logger.error("mqtt消息服务器异常：" + throwable.getMessage(), throwable);
    }
}
