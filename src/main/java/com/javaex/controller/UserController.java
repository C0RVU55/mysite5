package com.javaex.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.javaex.dao.UserDao;
import com.javaex.vo.UserVo;

@Controller
@RequestMapping(value="/user")
public class UserController {
	
	//필드
	@Autowired
	private UserDao uDao;

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
		
		uDao.insert(uVo);
		
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
		
		UserVo authUser = uDao.selectUser(uVo);
		//System.out.println("ctrl --> "+authUser);
		
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
}
