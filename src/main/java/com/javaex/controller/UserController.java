package com.javaex.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javaex.service.UserService;
import com.javaex.vo.UserVo;

@Controller
@RequestMapping(value="/user")
public class UserController {
	
	//필드 (컨트롤러는 서비스한테만 일시키고 서비스는 다오한테만 일시킴. 다오는 이제 DB접근 관련 기능만 갖게 됨)
	//@Autowired
	//private UserDao uDao;
	@Autowired
	private UserService userService;

	//회원가입폼
	@RequestMapping(value="/joinForm", method= {RequestMethod.GET, RequestMethod.POST})
	public String joinFrom() {
		System.out.println("/user/joinForm");
		
		return "user/joinForm";
	}
	
	//회원가입(회원정보 등록)
	@RequestMapping(value="/join", method= {RequestMethod.GET, RequestMethod.POST})
	public String join(@ModelAttribute UserVo uVo) {
		System.out.println("/user/join");
		System.out.println(uVo.toString());
		
		/* dao와 마찬가지로 테스트용
		int count = uDao.insert(uVo);
		System.out.println("UserController count "+count);
		*/
		
		//uDao.insert(uVo);
		
		//ctrl+클릭하면 메소드 있는 데로 넘어감. count로 받는 건 쓸 데 있을 때 쓰면 됨.
		int count = userService.join(uVo); 
		
		return "user/joinOk";
	}
	
	//로그인폼
	@RequestMapping(value="/loginForm", method= {RequestMethod.GET, RequestMethod.POST})
	public String loginForm() {
		System.out.println("/user/loginForm");
		
		return "user/loginForm";
	}
	
	//로그인
	@RequestMapping(value="/login", method= {RequestMethod.GET, RequestMethod.POST})
	public String login(@ModelAttribute UserVo uVo, HttpSession session) { //@모델 생략가능
		System.out.println("/user/login");
		System.out.println(uVo.toString());
		
		//UserVo authUser = uDao.selectUser(uVo);
		//System.out.println("ctrl --> "+authUser);
		
		UserVo authUser = userService.login(uVo);
		
		//로그인 실패 --> result파라미터 넘겨서 로그인폼 재요청해서 실패문구 띄우기
		if(authUser == null) {
			return "redirect:/user/loginForm?result=0";
		}
		
		//로그인 성공 --> 세션에 로그인정보 넣기
		session.setAttribute("authUser", authUser);
		
		return "redirect:/";
	}
	
	//로그아웃
	@RequestMapping(value="/logout", method= {RequestMethod.GET, RequestMethod.POST})
	public String logout(HttpSession session) {
		System.out.println("/user/logout");
		
		session.removeAttribute("authUser");
		session.invalidate();
		
		return "redirect:/";
	}
	
	//수정폼
	@RequestMapping(value="/mform", method= {RequestMethod.GET, RequestMethod.POST})
	public String modifyForm(HttpSession session, Model model) {
		System.out.println("/user/mform");
		
		//세션에서 no 가져오기
		UserVo vo = (UserVo)session.getAttribute("authUser");
		//int no = ((UserVo)session.getAttribute("authUser")).getNo(); --> 이게 나음
		
		//UserVo uVo = uDao.selectUser(vo.getNo());
		
		//회원정보 가져오기
		UserVo uVo = userService.modifyForm(vo.getNo());
		
		//jsp에 데이터 보내기
		model.addAttribute("uVo", uVo);
		
		return "user/modifyForm";
	}
	
	//수정
	@RequestMapping(value="/modify", method= {RequestMethod.GET, RequestMethod.POST})
	public String modify(@RequestParam("password") String password, 
					@RequestParam("name") String name, 
					@RequestParam("gender") String gender, 
					HttpSession session) {
		System.out.println("/user/modify");
		
		UserVo vo = (UserVo)session.getAttribute("authUser");
		UserVo uVo = new UserVo(vo.getNo(), password, name, gender);
		
		//파라미터로 안 받고 모델로 받았을 때 세션의 no 추가하는 법
		//userVo.setNo(vo.getNo());
		
		//uDao.modify(uVo);
		userService.modify(uVo);
		
		//세션 회원정보(이름) 갱신
		vo.setName(name);

		return "redirect:/";
	}
	
	//회원가입 - 아이디 체크 (UserService에 자세한 설명)
	@ResponseBody
	@RequestMapping(value="/idcheck", method= {RequestMethod.GET, RequestMethod.POST})
	public String idcheck(@RequestParam("id") String id) { //@ModelAttribute
		System.out.println("/user/idcheck");
		System.out.println("checkid= "+id); //패스워드는 테스트용
		
		String result = userService.idcheck(id);
		
		//아래처럼 여기에서 입력받은 값(회원정보)을 파라미터로 넘길 수도 있는데 항목이 많으면 입력, 수정이 다 번거로워서 안 함
		//return "redirect:/user/joinForm?result="+result;
		
		//리다이렉트나 포워드가 아니라(이거는 jsp파일 찾는 거) 데이터만 보내고 싶으면
		//--> @ResponseBody 추가 --> return이 기존방식이 아니라 response의 body영역에 데이터만 보내게 됨
		return result;
	}
}
