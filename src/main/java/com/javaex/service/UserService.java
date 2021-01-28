package com.javaex.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.UserDao;
import com.javaex.vo.UserVo;

@Service
public class UserService {
	
	//컨트롤 : 클라이언트랑 데이터 받고 주기
	//서비스 : 그외의 기능
	//dao : db랑 데이터 주고 받기 1번에 쿼리 1개만
	
	@Autowired
	private UserDao userDao;
	
	//회원가입(dao에 던져줌)
	public int join(UserVo uVo) {
		System.out.println("userService join()");

		return userDao.insert(uVo);
	}
	
	//로그인
	public UserVo login(UserVo uVo) {
		System.out.println("userService login()");
		
		return userDao.selectUser(uVo);
	}
	
	//수정폼
	public UserVo modifyForm(int no) {
		System.out.println("userService modifyForm()");
		
		return userDao.selectUser(no);
	}
	
	//수정
	public int modify(UserVo uVo) {
		System.out.println("userService modify()");
		
		return userDao.modify(uVo);
	}
}
