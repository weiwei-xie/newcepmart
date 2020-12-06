package com.iteye.wwwcomy.appdemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController1 {

	@GetMapping("/a")
	public String test1() {
		return "UserController1.test1()";
	}
}
