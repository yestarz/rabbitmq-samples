package link.yangxin.rabbitmqmqtt.mqtt.message;

import link.yangxin.rabbitmqmqtt.mqtt.pojo.MqttMessage;
import lombok.Data;

/**
 * MQTT消息路由器 规则
 *
 * @author yangxin
 * @date 2021/5/14
 */
@Data
public class MqttTopicRouterRule {

    private String topic;

    private String topicStartWith;

    private MqttMessageHandler handler;

    private MqttTopicMatcher matcher;

    private MqttTopicRouter routerBuilder;

    private boolean hasNext = false;

    public MqttTopicRouterRule(MqttTopicRouter router) {
        this.routerBuilder = router;
    }

    /**
     * 传入的topic与消息的topic相等
     *
     * @param topic
     * @return
     */
    public MqttTopicRouterRule topic(String topic) {
        this.topic = topic;
        return this;
    }

    /**
     * topic以某一个字符串开头
     *
     * @param regex
     * @return
     */
    public MqttTopicRouterRule topicStartWith(String regex) {
        this.topicStartWith = regex;
        return this;
    }

    /**
     * 自定义匹配规则
     *
     * @param matcher
     * @return
     */
    public MqttTopicRouterRule matcher(MqttTopicMatcher matcher) {
        this.matcher = matcher;
        return this;
    }

    /**
     * 设置规则处理器
     *
     * @param handler
     * @return
     */
    public MqttTopicRouterRule handler(MqttMessageHandler handler) {
        this.handler = handler;
        return this;
    }

    /**
     * 规则结束
     *
     * @return
     */
    public MqttTopicRouter end() {
        this.routerBuilder.getRules().add(this);
        return this.routerBuilder;
    }

    /**
     * 下一个规则
     *
     * @return
     */
    public MqttTopicRouter next() {
        this.hasNext = true;
        return this.end();
    }

    protected boolean test(MqttMessage message) {
        if (StringUtils.equals(this.topic, message.getTopic())) {
            return true;
        }
        if (StringUtils.startsWith(message.getTopic(), this.topicStartWith)) {
            return true;
        }
        if (this.matcher != null) {
            return this.matcher.match(message);
        }
        return false;
    }

    /**
     * 处理消息逻辑
     *
     * @param message
     */
    protected void service(MqttMessage message) {
        this.handler.handle(message);
    }
}
