package com.librosweb.libreria2.demo;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class DemoController {

	public DemoController() {
		super();
	}
	
	@PostMapping(value = "demo")
	public String welcome() {
		return "Welcom from secure endpoint";
	}
}
