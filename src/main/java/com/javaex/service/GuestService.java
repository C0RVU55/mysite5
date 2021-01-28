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
}
