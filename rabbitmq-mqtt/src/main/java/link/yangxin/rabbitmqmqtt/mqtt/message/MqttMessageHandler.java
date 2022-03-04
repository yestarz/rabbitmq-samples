package link.yangxin.rabbitmqmqtt.mqtt.message;


import link.yangxin.rabbitmqmqtt.mqtt.pojo.MqttMessage;

/**
 * MQTT消息处理器
 *
 * @author yangxin
 * @date 2021/5/14
 */
public interface MqttMessageHandler {

    /**
     * 处理消息
     *
     * @param message
     */
    void handle(MqttMessage message);

}
