package com.javaex.controller;

import java.util.List;
import java.util.Map;

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
	본인글 조회수 안 올라가게 하기 (세션을 controller에서 if문으로 받아서 조건에 따라 각 service로 보내는 게 나을듯)
	*/
	/*
	- 다이어그램에서 조회수 올리는 걸 먼저 써야 됨. 이 순서도 의미가 있음
	- 서비스에서 컨트롤러로 값 여러가지 넘기고 싶으면 map 사용
	- session은 웹브라우저에서만 쓰는 기술이라 controller에서 처리하는 게 좋음. 
	  (아니면 맞는 방법은 아니지만 controller에서 session 받아서 서비스에 넘기든가) 
	  왜냐하면 session이 service까지 들어갔을 경우 session에 관한 변경사항이 있을 때 service까지 수정해야 하기 때문. 
	  왠만하면 controller에서 끝내는 게 좋음.
	- 공식 시퀀스 다이어그램 문법 알아보기
	- 검색은 파라미터 request=false 써보기
	*/
	
	//리스트 (검색 추가 --> search 파라미터가 있을 수도 있고 없을 수도 있음)
	@RequestMapping(value="/list", method= {RequestMethod.GET, RequestMethod.POST})
	public String list(Model model, @RequestParam(value="search", required=false) String str) {
		System.out.println("/board/list --> "+str);
		
		List<BoardVo> bList = boardService.getList(str);
		//System.out.println("B ctrl list "+bList);
		
		model.addAttribute("bList", bList);
		
		return "board/list";
	}
	
	//리스트2 (+검색) --> required=false 이 파라미터값이 있을 수도 있고 없을 수도 있을 때 사용 (값이 없을 때 지정할 defaultValue도 넣음)
	@RequestMapping(value="/list2", method= {RequestMethod.GET, RequestMethod.POST})
	public String list2(Model model, @RequestParam(value="keyword", required=false, defaultValue="") String keyword) {
		System.out.println("/board/list2 --> "+keyword);
		
		List<BoardVo> bList = boardService.getList2(keyword);
		
		model.addAttribute("bList", bList);
		
		return "board/list2";
	}
	
	//리스트3 (+검색+페이징)
	@RequestMapping(value="/list3", method= {RequestMethod.GET, RequestMethod.POST})
	public String list3(Model model, @RequestParam(value="keyword", required=false, defaultValue="") String keyword,
						@RequestParam(value="crtPage", required=false, defaultValue="1") int crtPage) {
		System.out.println("/board/list3 --> "+keyword+" / "+crtPage);
		
		Map<String, Object> pageMap = boardService.getList3(keyword, crtPage);	
		System.out.println(pageMap);

		model.addAttribute("pMap", pageMap);
		
		return "board/list3";
	}
	
	//읽기
	@RequestMapping(value="/read", method= {RequestMethod.GET, RequestMethod.POST})
	public String read(@RequestParam("no") int no, Model model, HttpSession session) {
		System.out.println("/board/read");
		
		//System.out.println("/board/read "+boardService.read(no));
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		//컨트롤러에서는 복잡한 로직을 안 하는 게 낫지만 세션은 웹브라우저 기술이기 때문에 컨트롤러 선에서 끝냄. 
		if(authUser != null && authUser.getNo() == boardService.readOwn(no).getUserNo()) {
			BoardVo vo = boardService.readOwn(no);
			model.addAttribute("bVo", vo);
		} else {
			BoardVo bVo = boardService.read(no);
			model.addAttribute("bVo", bVo);
		}
				
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
