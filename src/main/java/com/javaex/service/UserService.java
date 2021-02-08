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
	
	//회원가입 - 아이디체크
	public String idcheck(String id) {
		System.out.println("userService idcheck() --> "+id);
		
		//System.out.println(userDao.selectOne(id));
		
		UserVo userVo = userDao.selectOne(id);
		
		String result="";
		
		//여태 서비스에서 이렇게 처리하고 끝냈는데 이러면 모든 정보 입력 후 중복체크하면 입력한 정보가 다 날아감. 
		//sp파일 자체를 재요청하는 거라서 --> ajax이용
		//자바스크립트로 서버에서 데이터를 가져와 페이지 전체 갱신없이 특정부분만 변경함. (주로 json으로 서버와 데이터 주고받음.)
		
		if(userVo == null) { //만약에 위나 dao에서 toString()을 찍으면 성공하는 경우만 되기 때문에 null일 때 오류남
			//사용할 수 있는 id
			result="pass";
		}else {
			//사용할 수 없는 id
			result="fail";
		}
		
		return result;	
	}
}
