package org.javaboy.manual;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.GetResponse;
import org.javaboy.manual.config.RabbitConfig;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class ManualApplicationTests {

    @Autowired
    RabbitTemplate rabbitTemplate;

	/**
	 * 拉模式的手动确认
	 */
	@Test
    void contextLoads() {
        long deliveryTag = 0;
        // 参数为是否事务模式
        Channel channel = rabbitTemplate.getConnectionFactory().createConnection().createChannel(false);
        try {
        	// 第二个参数为是否autoAck
            GetResponse getResponse = channel.basicGet(RabbitConfig.JAVABOY_QUEUE_NAME, false);
            deliveryTag = getResponse.getEnvelope().getDeliveryTag();
			String s = new String(getResponse.getBody());
			System.out.println("s = " + s);
			int i = 1 / 0;
			channel.basicAck(deliveryTag, false);
		} catch (IOException e) {
			try {
				channel.basicNack(deliveryTag, false, true);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
    }

    @Test
	void sendTooManyMsg(){
		for (int i = 0; i < 100; i++) {
			CorrelationData correlationData = new CorrelationData();
			correlationData.setId(i + "");
			rabbitTemplate.convertAndSend(RabbitConfig.JAVABOY_EXCHANGE_NAME, RabbitConfig.JAVABOY_QUEUE_NAME, "哈哈哈哈" + i, correlationData);
		}
	}

}
