package com.javaex.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	//리스트2
	public List<BoardVo> selectList2(String keyword){
		System.out.println("bDao selectList2() --> "+keyword);
		
		//System.out.println(sqlSession.selectList("board.selectList2", keyword));
		
		//아래 sqlSession 후에 쓰는 건 이름이 정해져 있음. 
		return sqlSession.selectList("board.selectList2", keyword);
	}
	
	//리스트3
	public List<BoardVo> selectList3(String keyword, int startRnum, int endRnum){
		System.out.println("bDao selectList3() --> "+keyword+" / "+startRnum+" / "+endRnum);
		
		//위 파라미터들은 이번만 쓰고 안 쓸 거 같으니까 vo를 만들기 보다 map으로 묶는 게 나음
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("keyword", keyword);
		map.put("startRnum", startRnum);
		map.put("endRnum", endRnum);
		
		System.out.println("bDao map --> "+map);
		
		//아래 sqlSession 후에 쓰는 건 이름이 정해져 있음. 
		return sqlSession.selectList("board.selectList3", map);
	}
	
	//전체 글 수 
	public int selectTotalCnt(String keyword) {
		System.out.println("bDao selectTotalCnt() --> "+keyword);
		
		return sqlSession.selectOne("board.selectTotalCnt", keyword);
	}
	
	//읽기
	public BoardVo selectPosting(int no) {
		System.out.println("bDao selectPosting()");
		
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
