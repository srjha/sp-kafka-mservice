package com.sr.practice.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

@Configuration
public class TestMsgConsumerConfig {

	@Value(value = "${kafka.bootstrapAdress}")
	private String bootstrapUrl;

	public static final String CONSUMER_GROUP_ID = "testConsumer2";

	@Bean
	public ConsumerFactory<Object, Object> consumerFactory() {

		Map<String, Object> configs = new HashMap<>();

		configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapUrl);
		configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		configs.put(ConsumerConfig.GROUP_ID_CONFIG, CONSUMER_GROUP_ID);

		System.out.println(" =========== Initializing consumer factory ==============");

		return new DefaultKafkaConsumerFactory<>(configs);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<Object, Object> cuncurrentListenerFactory() {

		ConcurrentKafkaListenerContainerFactory<Object, Object> concurrentKafkaListenerContainerFactory = new ConcurrentKafkaListenerContainerFactory<>();
		concurrentKafkaListenerContainerFactory.setConsumerFactory(consumerFactory());
		//concurrentKafkaListenerContainerFactory.setRecordFilterStrategy(r -> ((String) r.value()).conta );
		return concurrentKafkaListenerContainerFactory;
	}

}
