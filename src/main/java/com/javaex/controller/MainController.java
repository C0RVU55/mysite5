package com.javaex.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {

	//자동정렬할 때 줄 안 내려가게 하기 : window > preference > xml/jsp 검색 > xml file/html file > editor > line width
	//assets 경로 유의. image에는 회사 로고 등의 자원 위치. 
	//디자이너가 html로 주면 jsp로 복붙하고 구조 파악하고 코드 정렬함.
	//핸들러맵핑 조건 : spring-servlet에 있는 주소 아래 있는 파일이어야 되고 그 중에서 @Controller 붙은 거.
	//도메인은 톰캣 edit으로 수정 가능함. 대신 주소로 요청하는 css, image 경로가 달라져서 다 깨짐 --> ${pageContext.request.contextPath }
	//경로 바뀌어도 이미지는 안 깨지는 경우가 있는데 사이즈가 비슷하면 같은 걸로 인식하고 브라우저가 효율을 위해 캐시에 남는 걸 갖다 씀 
	// --> 강력한 새로고침(ctrl+f5) 또는 캐시삭제 또는 정해진 시간이 지나면 새로 다운받게 하는? 걸 설정 가능
	
	//css나 이미지는 브라우저에서 사이트랑 같이 한번에 다운받는 게 아니라 또 request 넣어서 css, 이미지도 계속 다운 받는 상태인 거
	//근데 리퀘스트 받는 게 프론트 컨트롤러고 스프링서블렛에 맞는 주소 아래 @컨트롤러 달고 있어서 핸들러맵핑에 있어야 되는데 이게 없어서 css등은 다운이 안 됨
	//대신 핸들러맵핑에 없으면 web.xml이 대신 찾도록 권한 넘겨주는 세팅 하면 됨
	
	//주소 "/"로 간소화하고 main은 복잡하지 않아서 @Controller에 주소 또 안 넣음.
	@RequestMapping(value="/", method=RequestMethod.GET) 
	public String main() {
		System.out.println("main");
		
		return "main/index";
	}
}
