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
	
	//ajax 등록 
	public void insertSeletKey(GuestVo gVo) {
		System.out.println("G dao insertSelectKey "+gVo);
		
		System.out.println("dao : xml 실행전--> "+gVo);
		sqlSession.insert("guestbook.insertSelectKey", gVo);
		System.out.println("dao : xml 실행후--> "+gVo);
		
		//return gVo.getNo(); 어차피 같은 GuestVo라 위 쿼리문 실행하면 no를 갖고 있는 상태가 됨. 굳이 no를 넘길 필요 없음.
	}
	
	//ajax 글 하나 가져오기
	public GuestVo select(int no) {
		System.out.println("G dao select --> "+no);
		
		return sqlSession.selectOne("guestbook.select", no);
	}

}
