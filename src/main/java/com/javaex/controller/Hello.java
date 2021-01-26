package com.javaex.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Hello {

	@RequestMapping(value="/hello")
	public String hello() {
		System.out.println("/mysite5/hello");
		return "/WEB-INF/views/index.jsp";
	}
}
