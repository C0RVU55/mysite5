package com.javaex.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.BoardDao;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

@Service
public class BoardService {

	//필드
	@Autowired
	private BoardDao bDao;
	
	//리스트
	public List<BoardVo> getList(String str) {
		System.out.println("B service getList()");
		
		//System.out.println("B service getList() "+bDao.selectListList());
		
		return bDao.selectList(str);
	}
	
	//읽기 + 조회수 증가
	public BoardVo read(int no) {
		System.out.println("B service read()");
		
		//System.out.println("B service read()"+bDao.selectPosting(no));
		
		//UserVo authUser = (UserVo)object;
		
		/* 컨트롤러에서 처리함
		//본인글인지 아닌지 확인 --> 조회수 증가시킨 후 게시글 정보 가져오기
		if(authUser != null || authUser.getNo() != bDao.selectPosting(no).getUserNo()) {
			bDao.hitUpdate(no);
		}
		*/
		
		bDao.hitUpdate(no);
		
		return bDao.selectPosting(no);
	}

	//읽기만 하기 (본인글은 조회수 증가 안 함)
	public BoardVo readOwn(int no) {
		System.out.println("B service readOwn() --> "+no);
		
		return bDao.selectPosting(no);
	}
	
	//쓰기
	public int write(BoardVo bVo) {
		System.out.println("B service write()");
		
		//System.out.println("B service write() "+bVo);
		
		return bDao.insert(bVo);
	}
	
	//삭제
	public int remove(int no) {
		System.out.println("B service remove()--> "+no);
		
		return bDao.delete(no);
	}
	
	//수정폼
	public BoardVo modifyForm(int no) {
		System.out.println("B sevice modifyForm()--> "+no);
		
		return bDao.selectModi(no);
	}
	
	//수정
	public int modify(BoardVo bVo) {
		System.out.println("B service modify() --> "+bVo);
		
		return bDao.update(bVo);
	}
	
}
