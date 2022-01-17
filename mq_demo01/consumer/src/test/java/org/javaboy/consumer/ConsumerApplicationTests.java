package org.javaboy.consumer;

import org.javaboy.consumer.config.RabbitConfig;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class ConsumerApplicationTests {

	@Resource
	private RabbitTemplate rabbitTemplate;

	@Test
	void contextLoads() {
		rabbitTemplate.convertAndSend(RabbitConfig.QUEUE_NAME,"哈哈哈");
	}

}
