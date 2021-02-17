package com.javaex.service;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.javaex.dao.GalleryDao;
import com.javaex.vo.GalleryVo;

@Service
public class GalleryService {
	
	@Autowired
	private GalleryDao galDao;
	
	//리스트
	public List<GalleryVo> getList() {
		System.out.println("[GalleryService.getList()]");
		
		return galDao.selectList();
	}
	
	//사진 보기
	public GalleryVo read(int no) {
		System.out.println("[GalleryService.read()]");
		
		return galDao.selectOne(no);
	}

	//사진 등록
	public void upload(MultipartFile file, String content, int userNo) {
		System.out.println("[GalleryService.upload()]");
		System.out.println(file.getOriginalFilename()); 
		
		/////DB에 저장할 정보 수집/////
		
		//서버 파일패스(경로)
		String saveDir = "C:\\javaStudy\\upload";
		
		//오리지널 파일명
		String orgName = file.getOriginalFilename();
		System.out.println("orgName: "+orgName);
		
		//확장자명
		String exName = orgName.substring(orgName.lastIndexOf("."));
		System.out.println("exName --> "+exName);
		
		//서버 저장파일명 + 확장자명
		String saveName = System.currentTimeMillis()+UUID.randomUUID().toString() + exName;
		System.out.println("savaName --> "+saveName);
		
		//서버 저장경로+파일명
		String filePath = saveDir + "\\" + saveName;
		System.out.println("filePath --> "+filePath);
		
		//파일 사이즈
		long fileSize = file.getSize();
		System.out.println("fileSize --> "+fileSize);
		
		//서버 하드디스크에 저장
		try {
			byte[] fileData = file.getBytes();
			OutputStream out = new FileOutputStream(filePath);
			BufferedOutputStream bos = new BufferedOutputStream(out);
			
			bos.write(fileData);
			bos.close();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		//DB에 저장
		GalleryVo galVo = new GalleryVo(userNo, content, filePath, orgName, saveName, fileSize);
		galDao.insert(galVo);
		
	}
}
