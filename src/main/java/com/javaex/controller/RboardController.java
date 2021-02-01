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
		
		return "rboard/rlist";
	}
	
	//읽기
	@RequestMapping(value="/read", method= {RequestMethod.GET, RequestMethod.POST})
	public String read(@RequestParam("no") int no, Model model) {
		System.out.println("/rboard/read");
		
		//System.out.println("/board/read "+boardService.read(no));
				
		BoardVo bVo = rboardService.read(no);
		
		model.addAttribute("bVo", bVo);
		
		return "rboard/read";
	}
	
	//글쓰기
	@RequestMapping(value="/wform", method= {RequestMethod.GET, RequestMethod.POST})
	public String rWriteForm(HttpSession session) {
		System.out.println("/rboard/wform");
		
		//로그인 여부 확인
		int userNo = ((UserVo)session.getAttribute("authUser")).getNo();
		
		return "rboard/rWriteForm";
	}
	
	//글 등록
	@RequestMapping(value="/rWrite", method= {RequestMethod.GET, RequestMethod.POST})
	public String write(@ModelAttribute BoardVo bVo, HttpSession session) {
		System.out.println("/board/rWrite");
		
		int userNo = ((UserVo)session.getAttribute("authUser")).getNo();
		
		bVo.setUserNo(userNo);
		//System.out.println("/board/write "+bVo);
		
		rboardService.rWrite(bVo);
		
		return "redirect:/rboard/rlist";
	}
	//답글쓰기폼 --> 지금 있는 jsp는 그냥 리스트, 글쓰기폼으로 이름 바꾸고 답글용 글쓰기폼 새로 만들기, 이름 바꾼 거 확인하기
	@RequestMapping(value="/rlist", method= {RequestMethod.GET, RequestMethod.POST})
	public String rWriteForm() {
		System.out.println("/rboard/rWriteForm");
		
		
		
		return "board/rWriteForm";
	}
	
	//답글쓰기
}
