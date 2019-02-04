package com.sr.practice.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableKafka
public class SpKafkaMserviceApplication {

	private static final String TEST_TOPIC = "testTopicOne";
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public static void main(String[] args) {
		SpringApplication.run(SpKafkaMserviceApplication.class, args);
	}

	@GetMapping("/k")
	String addMsg(@RequestParam("m") String msg) {

		ListenableFuture<SendResult<String, String>> send = kafkaTemplate.send(TEST_TOPIC, msg);

		ListenableFutureCallback<SendResult<String, String>> callback = new ListenableFutureCallback<SendResult<String, String>>() {

			@Override
			public void onFailure(Throwable ex) {
				System.out.println("Failed to post message");
			}

			@Override
			public void onSuccess(SendResult<String, String> result) {
				System.out.println("Successfilly posted message");
			}
		};
		send.addCallback(callback);
		return "OK";
	}

	@KafkaListener(topicPartitions = @TopicPartition(topic = TEST_TOPIC, partitionOffsets = {
			@PartitionOffset(partition = "0", initialOffset = "0") }), containerFactory = "cuncurrentListenerFactory")
	public void listen(@Payload String msg, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partId) {

		System.out.println("Received message: " + msg + " from partition id " + partId);
	}

}
