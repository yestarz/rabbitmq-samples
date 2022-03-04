package link.yangxin.rabbitmqmqtt.mqtt.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author yangxin
 * @date 2021/5/14
 */
@Configuration
@ConfigurationProperties(prefix = "emqx.server")
@Data
public class MqttProperties {

    private String broker;

    private String clientId;

    private String username;

    private String password;
}
