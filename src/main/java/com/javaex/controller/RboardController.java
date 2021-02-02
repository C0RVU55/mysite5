package com.javaex.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaex.service.RboardService;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

@Controller
@RequestMapping(value="/rboard")
public class RboardController {

	//필드
	@Autowired
	private RboardService rboardService;
	
	@RequestMapping(value="/rlist", method= {RequestMethod.GET, RequestMethod.POST})
	public String rlist(Model model) {
		System.out.println("/rboard/rlist");
		
		List<BoardVo> bList = rboardService.getRlist();
		//System.out.println("B ctrl list "+bList);
		
		model.addAttribute("bList", bList);
		
		return "rboard/rList";
	}
	
	//읽기
	@RequestMapping(value="/rRead", method= {RequestMethod.GET, RequestMethod.POST})
	public String rRead(@RequestParam("no") int no, Model model) {
		System.out.println("/rboard/rread");
		
		//System.out.println("/board/read "+boardService.read(no));
				
		BoardVo bVo = rboardService.rRead(no);
		
		model.addAttribute("bVo", bVo);
		
		return "rboard/rRead";
	}
	
	//글쓰기
	@RequestMapping(value="/rwform", method= {RequestMethod.GET, RequestMethod.POST})
	public String rWriteForm(HttpSession session) {
		System.out.println("/rboard/rwform");
		
		//로그인 여부 확인
		int userNo = ((UserVo)session.getAttribute("authUser")).getNo();
		
		return "rboard/rWriteForm";
	}
	
	//글 등록
	@RequestMapping(value="/rWrite", method= {RequestMethod.GET, RequestMethod.POST})
	public String rWrite(@ModelAttribute BoardVo bVo, HttpSession session) {
		System.out.println("/rboard/rWrite");
		
		int userNo = ((UserVo)session.getAttribute("authUser")).getNo();
		
		bVo.setUserNo(userNo);
		//System.out.println("/board/write "+bVo);
		
		rboardService.rWrite(bVo);
		
		return "redirect:/rboard/rlist";
	}
	
	//답글쓰기폼 
	@RequestMapping(value="reWriteForm", method= {RequestMethod.GET, RequestMethod.POST})
	public String reWriteForm() {
		System.out.println("/rboard/reWriteForm");
		
		//추후 로그인 여부 확인 추가
		
		return "rboard/reWriteForm";
	}
	
	//답글쓰기
	@RequestMapping(value="reWrite", method= {RequestMethod.GET, RequestMethod.POST})
	public String reWrite(@ModelAttribute BoardVo bVo) {
		System.out.println("/rboard/reWrite");
		
		rboardService.reWrite(bVo);
		
		return "redirect:/rboard/rlist";
	}
}
