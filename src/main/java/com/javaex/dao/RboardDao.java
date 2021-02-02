package com.javaex.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.BoardVo;

@Repository
public class RboardDao {

	//필드
	@Autowired
	private SqlSession sqlSession;
	
	//리스트
	public List<BoardVo> selectList() {
		System.out.println("bDao selectList()");
		
		//System.out.println(sqlSession.selectList("board.selectList"));
		
		return sqlSession.selectList("rboard.selectList");
	}
	
	//읽기
	public BoardVo selectPosting(int no) {
		System.out.println("bDao rRead()");
		
		//System.out.println("bDao read()"+sqlSession.selectOne("board.selectOne", no));
		
		return sqlSession.selectOne("rboard.selectPosting", no);		
	}
	
	//조회수 증가
	public void rHitUpdate(int no) {
		System.out.println("bDao hitUpdate() "+no);
		
		//int hit = sqlSession.update("board.hitUpdate", no);
		//System.out.println("hit "+hit);
		
		sqlSession.update("rboard.hitUpdate", no);
	}

	//쓰기
	public int insert(BoardVo bVo) {
		System.out.println("bDao insert()");
		
		//System.out.println("bDao insert() "+bVo);
		
		return sqlSession.insert("rboard.insert", bVo);
	}
	
	//답글쓰기
	public int reInsert(BoardVo bVo) {
		System.out.println("bDao insert()");
		
		//System.out.println("bDao insert() "+bVo);
		
		return sqlSession.insert("rboard.reInsert", bVo);
	}
	
	//열 정리 (같은 그룹 내에서 순서번호는 계속 늘어남)
	public void rawUpdate(BoardVo bVo) {
		System.out.println("bDao rawUpdate()");
		
		sqlSession.update("rboard.rawUpdate", bVo);
	}
	
	//들여쓰기
	public void depthUpdate(BoardVo bVo) {
		System.out.println("bDao depthUpdate()");
		
		sqlSession.update("rboard.depthUpdate", bVo);
		
	}
}
