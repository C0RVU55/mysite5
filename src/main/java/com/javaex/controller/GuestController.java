package com.javaex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.javaex.service.GuestService;
import com.javaex.vo.GuestVo;


@Controller
@RequestMapping(value="/guest")
public class GuestController {
	
	//필드
	@Autowired
	private GuestService guestService;
	
	//리스트
	@RequestMapping(value="/list", method= {RequestMethod.GET, RequestMethod.POST})
	public String list(Model model) {
		System.out.println("/guest/list");
		
		List<GuestVo> gList = guestService.getList();
		//System.out.println("G ctrl list-->"+gList);
		
		model.addAttribute("gList", gList);
	
		return "guestbook/addList";
	}
	
	//등록 --> @ModelAttribute
	@RequestMapping(value="/add", method= {RequestMethod.GET, RequestMethod.POST})
	public String add(@ModelAttribute GuestVo gVo) {
		System.out.println("/guest/add");
		
		//int count = guestService.add(gVo);
		//System.out.println("G ctrl add --> "+count);
		
		guestService.add(gVo);
		
		return "redirect:/guest/list";
	}
	
	//삭제폼 
	@RequestMapping(value="/dform", method= {RequestMethod.GET, RequestMethod.POST})
	public String deleteForm() { 
		System.out.println("deleteForm");
		
		return "guestbook/deleteForm";
	}
	
	//삭제
	@RequestMapping(value="/delete", method= {RequestMethod.GET, RequestMethod.POST})
	public String delete(@ModelAttribute GuestVo gVo) {
		System.out.println("/guest/delete");
		
		int count = guestService.delete(gVo);
		
		//비밀번호 틀렸을 경우 문구 출력
		if(count == 0) {
			return "redirect:/guest/dform?result=0&no="+gVo.getNo();
		}
		
		return "redirect:/guest/list";
	}
	
}
