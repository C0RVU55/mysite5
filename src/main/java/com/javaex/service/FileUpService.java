package com.javaex.service;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUpService {
	
	

	public String restore(MultipartFile file) {
		System.out.println("[FileUpService.restore()]");
		//file전달이 실패해도 file객체가 생성되기는 하기 때문에 이름이나 사이즈를 찍어야 확실히 확인 가능
		System.out.println(file.getOriginalFilename()); 
		
		/////DB에 저장할 정보 수집/////
		
		//서버 파일패스(경로) 주소 기호\\ 주의
		String saveDir = "C:\\javaStudy\\upload";
		
		//오리지널 파일명
		String orgName = file.getOriginalFilename();
		System.out.println("orgName: "+orgName);
		
		//확장자 / lastIndexOf --> 뒤에서부터 ""가 나오는 부분까지 자름
		String exName = orgName.substring(orgName.lastIndexOf("."));
		System.out.println("exName --> "+exName);
		
		//서버 저장파일명(파일명 유일해야 됨. 근데 랜덤으로 돌려도 중복될 수도 있기 때문에 UUID 앞에 currentTimeMillis(표준시간 사이에 생기는 숫자)를 추가함.)
		// + 확장자명(이거 안 하면 이미지가 아니라 그냥 파일로 저장됨)
		String saveName = System.currentTimeMillis()+UUID.randomUUID().toString() + exName;
		System.out.println("savaName --> "+saveName);
		
		//서버 저장경로+파일명
		String filePath = saveDir + "\\" + saveName;
		System.out.println("filePath --> "+filePath);
		
		//파일 사이즈
		long fileSize = file.getSize();
		System.out.println("fileSize --> "+fileSize);
		
		//서버 하드디스크에 저장(사용자컴에서 이동되는 게 아니라 복사되는 거)
		try {
			byte[] fileData = file.getBytes();
			OutputStream out = new FileOutputStream(filePath);
			BufferedOutputStream bos = new BufferedOutputStream(out);
			
			bos.write(fileData);
			bos.close();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return saveName;
		
	}
	
	
}
