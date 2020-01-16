package com.kenny.pact.demo.consumer;

import static org.assertj.core.api.Assertions.assertThat;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;

import com.kenny.pact.demo.HelloWorld;
import io.pactfoundation.consumer.dsl.LambdaDsl;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;



@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE,
		properties = "hello-world-service.base-url:http://localhost:${RANDOM_PORT}",
		classes = HelloWorldServiceConsumer.class)
public class HelloWorldServiceContractTest {

	@ClassRule
	public static RandomPortRule randomPort = new RandomPortRule();

	@Rule
	public PactProviderRuleMk2 provider = new PactProviderRuleMk2("hello-world-service", null, randomPort.getPort(), this);

	@Rule
	public ExpectedException expandException = ExpectedException.none();

	@Autowired
	private HelloWorldServiceConsumer helloWorldServiceConsumer;


	@Pact(consumer = "consumer")
	public RequestResponsePact pactHelloWorldAsJson(PactDslWithProvider builder) {
		// See https://github.com/DiUS/pact-jvm/tree/master/pact-jvm-consumer-junit#dsl-matching-methods
		DslPart body = LambdaDsl.newJsonBody((o) -> o
				.stringType("text", "Hello world!")).build();

		return builder.given(
				"Hello world Json exists")
				.uponReceiving("A request to /HelloWorldAsJson")
				.path("/HelloWorldAsJson")
				.method("GET")
				.willRespondWith()
				.status(200)
				.body(body)
				.toPact();

	}


	@Pact(consumer = "consumer")
	public RequestResponsePact pactHelloWorld(PactDslWithProvider builder) {


		return builder.given(
				"Hello world exists")
				.uponReceiving("A request to /HelloWorld")
				.path("/HelloWorld")
				.method("GET")
				.willRespondWith()
				.status(200)
				.body("Hello world!")
				.toPact();
	}



	@PactVerification(fragment = "pactHelloWorld")
	@Test
	public void helloWorldExists() {
		String text = helloWorldServiceConsumer.helloWorld();
		assertThat(text).isEqualTo("Hello world!");
	}

	@PactVerification(fragment = "pactHelloWorldAsJson")
	@Test
	public void helloWorldAsJsonExists() {
		HelloWorld helloWorld= helloWorldServiceConsumer.helloWorldAsJson();
		assertThat(helloWorld.getText()).isEqualTo("Hello world!");
	}



}