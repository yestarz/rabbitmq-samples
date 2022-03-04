package link.yangxin.rabbitmqmqtt.mqtt;

import link.yangxin.rabbitmqmqtt.mqtt.config.MqttProperties;
import org.fusesource.mqtt.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

/**
 * @author yangxin
 * @date 2021/5/14
 */
public class MqttClient {

    private static final Logger logger = LoggerFactory.getLogger(MqttClient.class);

    private MQTT mqtt;

    private CallbackConnection callbackConnection;

    private final MqttProperties mqttProperties;

    private final Listener listener;

    public MqttClient(MqttProperties mqttProperties, Listener listener) {
        this.mqttProperties = mqttProperties;
        this.listener = listener;
    }

    public void init() {
        try {
            buildConnection();
        } catch (URISyntaxException e) {
            logger.error("MQTT消息服务器初始化失败：" + e.getMessage(), e);
        }
    }

    public void disconnect() {
        this.callbackConnection.disconnect(new Callback<Void>() {
            @Override
            public void onSuccess(Void value) {
                logger.info("mqtt disconnected");
            }

            @Override
            public void onFailure(Throwable value) {
                logger.error("mqtt disconnect error", value);
            }
        });
    }

    /**
     * 创建MQTT客户端
     *
     * @throws URISyntaxException
     */
    public void buildMqtt() throws URISyntaxException {
        logger.info("========连接MQTT消息服务器 :{} ========", mqttProperties.getBroker());
        mqtt = new MQTT();
        mqtt.setHost(mqttProperties.getBroker());
        mqtt.setClientId(mqttProperties.getClientId());
        mqtt.setReconnectDelay(1000L);
        mqtt.setKeepAlive((short) 20);
        mqtt.setCleanSession(true);
        mqtt.setReconnectAttemptsMax(100);
        mqtt.setUserName(mqttProperties.getUsername());
        mqtt.setPassword(mqttProperties.getPassword());
    }

    /**
     * 初始化连接
     *
     * @throws URISyntaxException
     */
    public void buildConnection() throws URISyntaxException {
        if (this.mqtt == null) {
            buildMqtt();
        }
        callbackConnection = mqtt.callbackConnection();
        callbackConnection.listener(listener);
        callbackConnection.connect(new Callback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //连接成功后会默认订阅主题
                logger.info(mqttProperties.getClientId() + "连接成功");
            }

            @Override
            public void onFailure(Throwable throwable) {
                logger.error(throwable.getMessage(), throwable);
            }
        });

        callbackConnection.subscribe(new Topic[]{new Topic("mqtt", QoS.EXACTLY_ONCE)}, new Callback<byte[]>() {
            @Override
            public void onSuccess(byte[] value) {

            }

            @Override
            public void onFailure(Throwable value) {

            }
        });

    }


    /**
     * 向某个topic广播消息
     *
     * @param topic
     * @param content
     */
    public void publish(String topic, String content) {
        try {
            callbackConnection.publish(topic, content.getBytes(StandardCharsets.UTF_8), QoS.EXACTLY_ONCE, false, new Callback<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    logger.debug("{}发送消息成功:{}", topic, content);
                }

                @Override
                public void onFailure(Throwable throwable) {
                    logger.error(topic + "发送消息失败：" + throwable.getMessage() + "content:" + content, throwable);
                }
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

}
