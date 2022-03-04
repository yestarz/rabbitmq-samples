package link.yangxin.rabbitmqmqtt.mqtt.message;

import java.util.Objects;

/**
 * @author yangxin
 * @date 2022/3/4
 */
public class StringUtils {
    public static boolean equals(String topic, String topic1) {
        return Objects.equals(topic, topic1);
    }

    public static boolean startsWith(String topic, String topicStartWith) {
        return topic.startsWith(topicStartWith);
    }

    public static boolean isNotBlank(String message) {
        return message != null && message.trim().length() > 0;
    }
}
