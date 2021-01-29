package com.javaex.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.BoardDao;
import com.javaex.vo.BoardVo;

@Service
public class BoardService {

	//필드
	@Autowired
	private BoardDao bDao;
	
	//리스트
	public List<BoardVo> getList() {
		System.out.println("B service getList()");
		
		//System.out.println("B service getList() "+bDao.selectListList());
		
		return bDao.selectList();
	}
	
	//읽기 + 조회수 증가
	public BoardVo read(int no) {
		System.out.println("B service read()");
		
		//System.out.println("B service read()"+bDao.selectPosting(no));
		
		//조회수 증가시킨 후 게시글 정보 가져오기
		bDao.hitUpdate(no);
		
		return bDao.selectPosting(no);
	}

	
	//쓰기
	public int write(BoardVo bVo) {
		System.out.println("B service write()");
		
		//System.out.println("B service write() "+bVo);
		
		return bDao.insert(bVo);
	}
	
	
	
	
	
	
}
