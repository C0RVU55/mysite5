package com.javaex.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.BoardVo;

@Repository
public class RboardDao {

	//필드
	// 필드
	@Autowired
	private SqlSession sqlSession;
	
	//리스트
	public List<BoardVo> selectList() {
		System.out.println("bDao selectList()");
		
		//System.out.println(sqlSession.selectList("board.selectList"));
		
		return sqlSession.selectList("board.selectList");
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
}
