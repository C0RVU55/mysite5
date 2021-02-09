package com.javaex.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.GuestDao;
import com.javaex.vo.GuestVo;

@Service
public class GuestService {

	//필드
	@Autowired
	private GuestDao gDao;
	
	//리스트
	public List<GuestVo> getList() {
		System.out.println("G service getList()");
		
		//List<GuestVo> gList = gDao.getList();
		//System.out.println("--> "+gList);
		
		return gDao.getList();
	}
	
	//등록
	public int add(GuestVo gVo) {
		System.out.println("G service add()");
		
		//int count = gDao.add(gVo);
		//System.out.println(count);
		
		return gDao.add(gVo);
	}
	
	//삭제
	public int delete(GuestVo gVo) {
		System.out.println("G service delete()");
		
		int count = gDao.delete(gVo);
		
		return count;
	}
	
	//ajax 글저장 --> 저장한 글 리턴
	public GuestVo writeResultVo(GuestVo gVo) {
		//글저장 + 글번호 가져오기
		System.out.println("service : dao.insertSelectKey() 실행 전 -->"+gVo);
		gDao.insertSeletKey(gVo);
		System.out.println("service : dao.insertSelectKey() 실행 후 -->"+gVo);
		int no = gVo.getNo(); //no가 들어온 걸 확인 가능
		
		//방금 저장한 글 조회
		//가장 최근 글 조회 --> 내가 저장한 글이 올지 알 수 없으니까 입력한 글의 번호로 글 가져옴
		GuestVo vo = gDao.select(no);
		System.out.println("G service writeResultVo()--> "+vo);
		
		return vo;
	}
}
