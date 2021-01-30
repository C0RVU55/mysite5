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

import com.javaex.service.BoardService;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

@Controller
@RequestMapping(value="/board")
public class BoardController {

	//필드
	@Autowired
	private BoardService boardService;
	
	/*
	1.본인글 조회수 안 올라가게 하기
	2.검색 추가하기
	*/
	
	//리스트
	@RequestMapping(value="/list", method= {RequestMethod.GET, RequestMethod.POST})
	public String list(Model model) {
		System.out.println("/board/list");
		
		List<BoardVo> bList = boardService.getList();
		//System.out.println("B ctrl list "+bList);
		
		model.addAttribute("bList", bList);
		
		return "board/list";
	}
	
	//읽기
	@RequestMapping(value="/read", method= {RequestMethod.GET, RequestMethod.POST})
	public String read(@RequestParam("no") int no, Model model) {
		System.out.println("/board/read");
		
		//System.out.println("/board/read "+boardService.read(no));
				
		BoardVo bVo = boardService.read(no);
		
		model.addAttribute("bVo", bVo);
		
		return "board/read";
	}
	
	//글쓰기
	@RequestMapping(value="/wform", method= {RequestMethod.GET, RequestMethod.POST})
	public String writeForm(HttpSession session) {
		System.out.println("/board/wform");
		
		//로그인 여부 확인
		int userNo = ((UserVo)session.getAttribute("authUser")).getNo();
		
		return "board/writeForm";
	}
	
	//글 등록
	@RequestMapping(value="/write", method= {RequestMethod.GET, RequestMethod.POST})
	public String write(@ModelAttribute BoardVo bVo, HttpSession session) {
		System.out.println("/board/write");
		
		int userNo = ((UserVo)session.getAttribute("authUser")).getNo();
		
		bVo.setUserNo(userNo);
		//System.out.println("/board/write "+bVo);
		
		boardService.write(bVo);
		
		return "redirect:/board/list";
	}
	
	//삭제
	@RequestMapping(value="/remove", method= {RequestMethod.GET, RequestMethod.POST})
	public String remove(@RequestParam("no") int no) {
		System.out.println("/board/remove --> "+no);
		
		boardService.remove(no);
		
		return "redirect:/board/list";
	}
	
	//수정폼
	@RequestMapping(value="/mform", method= {RequestMethod.GET, RequestMethod.POST})
	public String modifyForm(@RequestParam("no") int no, Model model) {
		System.out.println("/board/mform --> "+no);
		
		BoardVo bVo = boardService.modifyForm(no);
		
		model.addAttribute("bVo", bVo);
		
		return "board/modifyForm";
	}
	
	//수정
	@RequestMapping(value="/modify", method= {RequestMethod.GET, RequestMethod.POST})
	public String modify(@ModelAttribute BoardVo bVo) {
		System.out.println("/board/modify --> "+bVo);
		
		boardService.modify(bVo);
		
		return "redirect:/board/list";
	}
	
}
