package com.kenny.pact.demo.provider;

import com.kenny.pact.demo.HelloWorld;
import org.springframework.stereotype.Service;

@Service
public class HelloWorldService {

	public String helloWorld() {
		return "Hello world!";
	}

	public HelloWorld helloWorldAsJson() {
		return new HelloWorld("Hello world!");
	}
}
