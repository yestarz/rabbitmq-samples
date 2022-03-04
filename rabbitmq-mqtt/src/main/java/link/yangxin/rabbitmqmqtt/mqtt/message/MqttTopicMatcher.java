package link.yangxin.rabbitmqmqtt.mqtt.message;


import link.yangxin.rabbitmqmqtt.mqtt.pojo.MqttMessage;

/**
 * 自定义的topic匹配器
 *
 * @author yangxin
 * @date 2021/5/14
 */
public interface MqttTopicMatcher {

    /**
     * 是否匹配
     *
     * @param message
     * @return
     */
    boolean match(MqttMessage message);

}
