package com.javaex.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.GuestVo;

@Repository
public class GuestDao {

	// 필드
	@Autowired
	private SqlSession sqlSession;
	
	// 생성자
	// 메소드 겟셋
	// 메소드 일반

	// 내용 출력(리스트)
	public List<GuestVo> getList() {
		
		//List<GuestVo> gList = sqlSession.selectList("guestbook.selectList");
		//System.out.println("G dao list --> "+gList.toString());
		
		return sqlSession.selectList("guestbook.selectList");
	}
	
	// 내용 등록
	public int add(GuestVo gVo) {
		System.out.println("G dao add "+gVo);
		
		//int count = sqlSession.insert("guestbook.insert", gVo);
		
		return sqlSession.insert("guestbook.insert", gVo);
	}

	// 내용 삭제 (조건 2개 달아서 쿼리문 1개로 처리)
	public int delete(GuestVo gVo) {
		System.out.println("G dao delete "+gVo);
		
		int count = sqlSession.delete("guestbook.delete", gVo);
		
		return count;
	}

}
