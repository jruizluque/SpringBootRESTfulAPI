package com.in28minutes.rest.webservices.restfulwebservices;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

	@GetMapping(path = "/hellow-world")
	public String helloWorld() {
		return "Hello World!";
	}
	
	@GetMapping(path = "/hellow-world-bean")
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("Hello World!");
	}
	
}
