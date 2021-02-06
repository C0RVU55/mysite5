package com.javaex.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.BoardVo;

@Repository
public class BoardDao {
	
	// 필드
	@Autowired
	private SqlSession sqlSession;

	// 생성자
	// 메소드 겟셋
	// 메소드 일반
	
	//리스트
	public List<BoardVo> selectList(String str) {
		System.out.println("bDao selectList()");
		
		//System.out.println(sqlSession.selectList("board.selectList"));
		
		return sqlSession.selectList("board.selectList", str);
	}
	
	//읽기
	public BoardVo selectPosting(int no) {
		System.out.println("bDao read()");
		
		//System.out.println("bDao read()"+sqlSession.selectOne("board.selectOne", no));
		
		return sqlSession.selectOne("board.selectPosting", no);		
	}
	
	//조회수 증가
	public void hitUpdate(int no) {
		System.out.println("bDao hitUpdate() "+no);
		
		//int hit = sqlSession.update("board.hitUpdate", no);
		//System.out.println("hit "+hit);
		
		sqlSession.update("board.hitUpdate", no);
	}

	//쓰기
	public int insert(BoardVo bVo) {
		System.out.println("bDao insert()");
		
		//System.out.println("bDao insert() "+bVo);
		
		return sqlSession.insert("board.insert", bVo);
	}

	//삭제
	public int delete(int no) {
		System.out.println("bDao delete()--> "+no);
		
		return sqlSession.delete("board.delete", no);
	}

	//수정폼 정보 불러오기
	public BoardVo selectModi(int no) {
		System.out.println("bDao selectModi()--> "+no);
		
		//BoardVo vo = sqlSession.selectOne("board.selectPosting", no);
		//System.out.println(vo);
		
		return sqlSession.selectOne("board.selectPosting", no);
	}
	
	//수정
	public int update(BoardVo bVo) {
		System.out.println("bDao update()--> "+bVo);
		
		return sqlSession.update("board.update", bVo);
	}
}
