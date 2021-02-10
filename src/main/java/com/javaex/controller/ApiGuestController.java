package com.javaex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	//api는 주소로는 안 보이는 영역
	//리스트는 GuestController에 있는 리스트처럼 jsp불러오는 게 아니라 데이터만 불러오는 방식
	
	//전체 리스트 가져오기(ajax)
	@ResponseBody
	@RequestMapping(value="/ajaxList", method= {RequestMethod.GET, RequestMethod.POST})
	public List<GuestVo> getList() {
		System.out.println("[ApiGuestCtrl /list");
		
		return guestService.getList();
	}
	
	//글작성(ajax)
	@ResponseBody
	@RequestMapping(value="/write", method= {RequestMethod.GET, RequestMethod.POST})
	public GuestVo write(@ModelAttribute GuestVo guestVo) {
		System.out.println("[ApiGuestCtrl] /write");
		System.out.println(guestVo);
		
		GuestVo gVo = guestService.writeResultVo(guestVo);
		
		//model로 받은(파라미터로 받은) vo를 response의 body영역에 담아서(json으로) 보냄.
		
		return gVo;
	}
	
	//글작성(ajax)2
	@ResponseBody
	@RequestMapping(value="/write2", method= {RequestMethod.GET, RequestMethod.POST})
	public GuestVo write2(@RequestBody GuestVo guestVo) { //위와 다르게 파라미터가 아닌 바디 영역에 있는 데이터를(json으로) 받을 수 있음.
		System.out.println("[ApiGuestCtrl] /write2");
		System.out.println(guestVo);
		
		GuestVo gVo = guestService.writeResultVo(guestVo);
		
		return gVo;
	}

	//삭제(ajax)
	@ResponseBody
	@RequestMapping(value="/remove", method= {RequestMethod.GET, RequestMethod.POST})
	public int remove(@ModelAttribute GuestVo guestVo) {
		System.out.println("[ApiGuestCtrl] /remove --> "+guestVo); 
		
		int count = guestService.remove(guestVo);
		System.out.println(count);
		
		//비번 맞는지 틀리는지 따지기 위해 count를 바디 영역에 보냄.
		return count;
	}
	
}
