package com.sr.practice.kafka;

import java.util.Collections;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class TestTopicConfig {

	@Value(value = "${kafka.bootstrapAdress}")
	private String bootstrapUrl;

	@Bean
	public KafkaAdmin kafkaAdmin() {

		Map<String, Object> config = Collections.singletonMap(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapUrl);
		return new KafkaAdmin(config);
	}

	@Bean
	public NewTopic testTopic() {
		return new NewTopic("testTopicOne", 1, (short) 1);
	}

}
