package com.javaex.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.GalleryVo;

@Repository
public class GalleryDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	public void insert(GalleryVo galVo) {
		System.out.println("[GalleryDao] --> "+galVo);
		sqlSession.insert("gallery.insert", galVo);
	}

}
