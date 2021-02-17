package com.javaex.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.GalleryVo;

@Repository
public class GalleryDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	//리스트
	public List<GalleryVo> selectList() {
		System.out.println("[GalleryDao.selectList()]");
		
		//List<GalleryVo> gList = sqlSession.selectList("gallery.selectList");
		//System.out.println("[GalleryDao.selectList()] --> "+gList);
		
		return sqlSession.selectList("gallery.selectList");
	}
	
	//사진 정보
	public GalleryVo selectOne(int no) {
		System.out.println("[GalleryDao.selectOne()]");
		
		return sqlSession.selectOne("gallery.selectOne", no);
	}
	
	//DB 등록
	public void insert(GalleryVo galVo) {
		System.out.println("[GalleryDao.insert()] --> "+galVo);
		
		sqlSession.insert("gallery.insert", galVo);
	}

}
