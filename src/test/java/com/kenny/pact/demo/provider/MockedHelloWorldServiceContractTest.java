package com.kenny.pact.demo.provider;

import static org.mockito.Mockito.when;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;
import au.com.dius.pact.provider.spring.SpringRestPactRunner;
import au.com.dius.pact.provider.spring.target.SpringBootHttpTarget;
import com.kenny.pact.demo.HelloWorld;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@RunWith(SpringRestPactRunner.class)
@Provider("hello-world-service")
@PactFolder("target/pacts")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MockedHelloWorldServiceContractTest {

	@TestTarget
	public final Target target = new SpringBootHttpTarget();

	@MockBean
	private HelloWorldService helloWorldService;

	@State("Hello world exists")
	public void helloWorldExists() {
		when(helloWorldService.helloWorld()).thenReturn("Hello world!");
	}

	@State("Hello world Json exists")
	public void helloWorldAsJsonExists() {
		when(helloWorldService.helloWorldAsJson()).thenReturn(HelloWorld.builder().text("Hello world!").build());
	}


}
