package com.company.CommentQueue;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class CommentQueueApplication {

	public static final String TOPIC_EXCHANGE_NAME = "comment-exchange";

	@Bean
	public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	TopicExchange exchange() {
		return new TopicExchange(TOPIC_EXCHANGE_NAME);
	}

	// First Queue for Creating and Updating Notes
	public static final String QUEUE_NAME = "comment-queue";
	public static final String ROUTING_KEY = "comment.#";

	@Bean
	Queue queue() {
		return new Queue(QUEUE_NAME, false);
	}

	@Bean
	Binding binding(Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
	}
//	//A second Queue for deletions
//	public static final String DELETE_QUEUE_NAME = "note-delete-queue";
//	public static final String DELETE_ROUTING_KEY = "noteDelete.#";
//
//	@Bean
//	Queue deleteQueue() {
//		return new Queue(DELETE_QUEUE_NAME, false);
//	}
//
//	@Bean
//	Binding deleteBinding(Queue deleteQueue, TopicExchange exchange) {
//		return BindingBuilder.bind(deleteQueue).to(exchange).with(DELETE_ROUTING_KEY);
//	}
//
	public static void main(String[] args) {
		SpringApplication.run(CommentQueueApplication.class, args);
	}

}
