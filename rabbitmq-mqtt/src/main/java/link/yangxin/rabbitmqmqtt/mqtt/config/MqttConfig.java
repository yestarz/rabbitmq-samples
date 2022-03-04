package link.yangxin.rabbitmqmqtt.mqtt.config;

import link.yangxin.rabbitmqmqtt.mqtt.MqttClient;
import link.yangxin.rabbitmqmqtt.mqtt.MqttListener;
import link.yangxin.rabbitmqmqtt.mqtt.message.MqttTopicRouter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yangxin
 * @date 2021/5/14
 */
@Configuration
public class MqttConfig {

    protected static final Logger logger = LoggerFactory.getLogger(MqttConfig.class);

    @Bean(initMethod = "init", destroyMethod = "disconnect")
    public MqttClient mqttClient(MqttProperties mqttProperties, MqttListener mqttListener) {
        return new MqttClient(mqttProperties, mqttListener);
    }

    @Bean
    public MqttListener mqttListener() {
        MqttListener mqttListener = new MqttListener();
        MqttTopicRouter router = new MqttTopicRouter().rule().topicStartWith("mqtt").handler((handler) -> {
            System.out.println("收到消息：" + handler.getMessage());
        }).end();
        mqttListener.setRouter(router);
        return mqttListener;
    }
}
