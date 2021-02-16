package com.javaex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.javaex.service.FileUpService;

@Controller
@RequestMapping(value="fileUp")
public class FileUpController {
	
	@Autowired
	private FileUpService fileUpService;
	
	@RequestMapping(value="/form", method={RequestMethod.GET, RequestMethod.POST})
	public String form() {
		System.out.println("[fileUpCtrl.form()]");
		
		return "/fileUpload/form";
	}
	
	@RequestMapping(value="/upload", method={RequestMethod.GET, RequestMethod.POST})
	public String upload(@RequestParam("file") MultipartFile file, Model model) {
		System.out.println("[fileUpCtrl.upload()]");
		
		//파일명, 사이즈 출력
		//System.out.println(file.getOriginalFilename());
		//System.out.println(file.getSize());
		
		String saveName = fileUpService.restore(file);
		System.out.println("[fileUpCtrl.upload()] --> "+saveName);
		
		model.addAttribute("saveName", saveName); //모델로 넘길 때 키값 쓰는 거 주의
		
		return "/fileUpload/result";
	}
}
