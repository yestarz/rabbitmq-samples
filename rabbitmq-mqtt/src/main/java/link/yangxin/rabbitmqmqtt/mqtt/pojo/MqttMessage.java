package link.yangxin.rabbitmqmqtt.mqtt.pojo;

import com.alibaba.fastjson.JSON;
import link.yangxin.rabbitmqmqtt.mqtt.message.StringUtils;
import lombok.Data;

import java.io.Serializable;

/**
 * MQTT消息
 *
 * @author yangxin
 * @date 2021/5/14
 */
@Data
public class MqttMessage implements Serializable {

    /**
     * 消息的topic
     */
    private String topic;

    /**
     * 消息的内容
     */
    private String message;

    public MqttMessage() {
    }

    public MqttMessage(String topic, String message) {
        this.topic = topic;
        this.message = message;
    }

    public <T> T parseObject(Class<T> clazz) {
        if (StringUtils.isNotBlank(message)) {
            return JSON.parseObject(message, clazz);
        }
        return null;
    }
}
