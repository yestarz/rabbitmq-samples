package link.yangxin.rabbitmqmqtt.mqtt.message;

import link.yangxin.rabbitmqmqtt.mqtt.pojo.MqttMessage;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * MQTT消息路由器,根据自定义的路由规则，将收到的消息交给不同的处理器去处理
 *
 * @author yangxin
 * @date 2021/5/14
 */
@Data
public class MqttTopicRouter {

    /**
     * 规则路由列表
     */
    private List<MqttTopicRouterRule> rules = new ArrayList<>();

    /**
     * 开始一个新的Route规则.
     */
    public MqttTopicRouterRule rule() {
        return new MqttTopicRouterRule(this);
    }

    /**
     * 处理mqtt消息
     *
     * @param message
     */
    public void route(MqttMessage message) {
        List<MqttTopicRouterRule> matchRules = new ArrayList<>();
        for (MqttTopicRouterRule rule : this.rules) {
            if (rule.test(message)) {
                matchRules.add(rule);
                if (!rule.isHasNext()) {
                    // 匹配成功一个就到此为止
                    break;
                }
            }
        }
        if (CollectionUtils.isEmpty(matchRules)) {
            return;
        }
        for (MqttTopicRouterRule matchRule : matchRules) {
            matchRule.service(message);
        }
    }
}
