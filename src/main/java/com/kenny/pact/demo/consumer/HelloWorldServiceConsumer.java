package com.kenny.pact.demo.consumer;

import com.kenny.pact.demo.HelloWorld;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class HelloWorldServiceConsumer {

	private final RestTemplate restTemplate;

	public HelloWorldServiceConsumer(@Value("${hello-world-service.base-url}") String baseUrl) {
		System.out.println("URL:" + baseUrl);
		this.restTemplate = new RestTemplateBuilder().rootUri(baseUrl).build();
	}

	public String helloWorld() {
		return restTemplate.getForObject("/HelloWorld", String.class);
	}

	public HelloWorld helloWorldAsJson() {
		return restTemplate.getForObject("/HelloWorldAsJson", HelloWorld.class);
	}


}
