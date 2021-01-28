package com.javaex.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.UserVo;

@Repository
public class UserDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	//회원가입 --> 회원정보 저장
	public int insert(UserVo uVo) {
		System.out.println("user dao insert");
		System.out.println(uVo.toString());
		
		/*
		int count = sqlSession.insert("user.insert", uVo);
		//sql확인 (duplicatekey어쩌구 오류는 아이디(pk)중복 오류)
		System.out.println("user insert count: "+count);
		*/
		
		//count변수는 테스트할 때 쓰고 작동하면 sql문을 return하면 됨. 어차피 1은 주는 거니까.
		return sqlSession.insert("user.insert", uVo);
	}
	
	//로그인 (데이터 잘 받아오나 테스트할 때는 void로 함)
	public UserVo selectUser(UserVo uVo) {
		System.out.println("user dao selectUser");
		System.out.println(uVo.toString());
		
		/* 테스트
		//selectOne이지만 쿼리문에 쓰는 태그는 <select>
		
		UserVo vo = sqlSession.selectOne("user.selectUser", uVo); 
		System.out.println("user dao selectUser 2 "+vo.toString());
		*/
		
		return sqlSession.selectOne("user.selectUser", uVo);
	}
	
	//회원 정보 가져오기 (로그인 메소드 오버로딩)
	public UserVo selectUser(int no) {
		System.out.println("user dao mform");
		
		//UserVo vo = sqlSession.selectOne("user.selectUser2", no);
		//System.out.println("user dao mform -->"+vo);
		
		return sqlSession.selectOne("user.selectUser2", no);
	}
	
	//수정
	public int modify(UserVo uVo) {
		System.out.println("user dao modify");
		
		//System.out.println(uVo.toString());
		
		//int count = sqlSession.update("user.update", uVo);
		//System.out.println("user dao modify count: "+count);
		
		return sqlSession.update("user.update", uVo);
	}
}
