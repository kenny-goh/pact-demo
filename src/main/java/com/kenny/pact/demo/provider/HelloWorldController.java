package com.kenny.pact.demo.provider;

import com.kenny.pact.demo.HelloWorld;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

	private final HelloWorldService helloWorldService;

	public HelloWorldController(HelloWorldService helloWorldService) {
		this.helloWorldService = helloWorldService;
	}

	@GetMapping("/HelloWorld")
	public String helloWorld() {
		return helloWorldService.helloWorld();
	}

	@GetMapping("/HelloWorldAsJson")
	public HelloWorld helloWorldAsJson() {
		return helloWorldService.helloWorldAsJson();
	}


}