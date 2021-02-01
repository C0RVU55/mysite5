package com.javaex.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.javaex.dao.RboardDao;
import com.javaex.vo.BoardVo;

@Service
public class RboardService {

	//필드
	private RboardDao rboardDao;
	
	//리스트
	public List<BoardVo> getRlist() {
		System.out.println("B service getList()");
		
		//System.out.println("B service getList() "+bDao.selectListList());
		
		return rboardDao.selectList();
	}
	
	//읽기 + 조회수 증가
	public BoardVo read(int no) {
		System.out.println("B service read()");
		
		//System.out.println("B service read()"+bDao.selectPosting(no));
		
		//UserVo authUser = (UserVo)object;
		
		rboardDao.hitUpdate(no);
		
		return rboardDao.selectPosting(no);
	}

	
	//쓰기
	public int rWrite(BoardVo bVo) {
		System.out.println("B service write()");
		
		//System.out.println("B service write() "+bVo);
		
		return rboardDao.insert(bVo);
	}
}
