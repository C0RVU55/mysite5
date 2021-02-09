package com.javaex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javaex.service.GuestService;
import com.javaex.vo.GuestVo;

@Controller
@RequestMapping(value="/api/guest")
public class ApiGuestController {
	
	@Autowired
	GuestService guestService;
	
	//주소로는 안 보이는 영역
	
	//잔체 리스트 가져오기(ajax)
	@ResponseBody
	@RequestMapping(value="/list")
	public List<GuestVo> list() {
		System.out.println("[ApiGuestCtrl /list");
		
		return guestService.getList();
	}
	
	//글작성(ajax)
	@ResponseBody
	@RequestMapping(value="/write", method= {RequestMethod.GET, RequestMethod.POST})
	public GuestVo Write(@ModelAttribute GuestVo guestVo) {
		System.out.println("[ApiGuestCtrl] /write");
		System.out.println(guestVo);
		
		GuestVo gVo = guestService.writeResultVo(guestVo);
		
		//model로 보냈던 vo를 response의 body영역에 담아서 보냄. json으로 보냄
		
		return gVo;
	}

	//리스트(게스트컨트롤러에 있는 리스트처럼 jsp불러오는 게 아니라 데이터만 부르는 거)
}
