package com.javaex.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.javaex.service.GalleryService;
import com.javaex.vo.GalleryVo;
import com.javaex.vo.UserVo;

@Controller
@RequestMapping(value="/gallery")
public class GalleryController {
	
	@Autowired
	private GalleryService galService;

	//사진 리스트 출력 (ajax)
	@RequestMapping(value="/list", method={RequestMethod.GET, RequestMethod.POST})
	public String list(Model model) {
		System.out.println("[GalleryCtrl.list()]");
		
		List<GalleryVo> galList = galService.getList();
		System.out.println(galList);

		model.addAttribute("galList", galList);
		
		return "/gallery/list";
	}
	
	//사진 등록(form방식, 리스트페이지로 리다이렉트)
	@RequestMapping(value="/upload", method={RequestMethod.GET, RequestMethod.POST})
	public String upload(@RequestParam("content") String content, @RequestParam("file") MultipartFile file, HttpSession session) {
		System.out.println("[GalleryCtrl.upload()]");
		
		//파일명, 사이즈 출력
		//System.out.println(file.getOriginalFilename());
		//System.out.println(file.getSize());
		
		//세션받아서 userNo 넘기기
		int userNo = ((UserVo)session.getAttribute("authUser")).getNo();
		String userName = ((UserVo)session.getAttribute("authUser")).getName();
		
		galService.upload(file, content, userNo, userName);
		
		//리스트로 리다이렉트
		return "redirect:/gallery/list";
	}
	
}
