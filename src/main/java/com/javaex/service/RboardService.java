package com.javaex.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.RboardDao;
import com.javaex.vo.BoardVo;

@Service
public class RboardService {

	// 필드
	@Autowired
	private RboardDao rboardDao;

	// 리스트
	public List<BoardVo> getRlist() {
		System.out.println("B service getrList()");

		// System.out.println("B service getList() "+bDao.selectListList());

		return rboardDao.selectList();
	}

	// 읽기 + 조회수 증가
	public BoardVo rRead(int no) {
		System.out.println("B service rread()");

		// System.out.println("B service read()"+bDao.selectPosting(no));

		// UserVo authUser = (UserVo)object;

		rboardDao.rHitUpdate(no);

		return rboardDao.selectPosting(no);
	}

	// 글쓰기
	public int rWrite(BoardVo bVo) {
		System.out.println("B service rwrite()");

		// System.out.println("B service write() "+bVo);

		return rboardDao.insert(bVo);
	}

	// 답글쓰기
	public void reWrite(BoardVo bVo) {
		System.out.println("B service rewrite()");
		
		bVo.setOrderNo(bVo.getOrderNo()+1);
		bVo.setDepth(bVo.getDepth()+1);
		
		rboardDao.rawUpdate(bVo);
		rboardDao.reInsert(bVo);

	}
}
