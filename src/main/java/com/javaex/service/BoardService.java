package com.javaex.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.BoardDao;
import com.javaex.vo.BoardVo;

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
	
	//리스트2
	public List<BoardVo> getList2(String keyword){
		System.out.println("B service getList2() --> "+keyword);
		
		return bDao.selectList2(keyword);
	}
	
	//리스트3
	public Map<String, Object> getList3(String keyword, int crtPage){
		System.out.println("B service getList3() --> "+keyword+" / "+crtPage);
	
		///////////////////리스트 출력///////////////////////
		
		//crtPage --> 시작번호, 끝번호 구해야 됨. 1페이지면 1, 10번, 2페이지면 11, 20번
		
		//페이지당 글 수 먼저 정함
		int listCnt = 10;
		
		//현재 페이지(페이지 범위 외 숫자 넣으면 1페이지 가도록)
		crtPage = (crtPage > 0) ? crtPage : (crtPage = 1);
		
		/* *****아래 if문을 위 삼항연산자로 표현 가능*****
		if(crtPage > 0) {
			crtPage = crtPage;
		} else {
			crtPage = 1;
		}
		*/
		
		//시작 글번호 startRnum --> (현재페이지 - 1) * 페이지당 글 개수 + 1
		int startRnum = (crtPage - 1) * listCnt + 1;
		
		//끝 글번호 endRnum
		int endRnum = (startRnum + listCnt) - 1;
		
		List<BoardVo> bList = bDao.selectList3(keyword, startRnum, endRnum);
		
		///////////////////페이징 계산///////////////////////
		
		//페이지당 버튼 수 먼저 정함
		int pageBtnCount = 5;
		
		//전체 글 수
		int totalCount = bDao.selectTotalCnt(keyword);
		
		//1 --> 1~5 / 6 --> 6~10
		//1/5.0 --> 1.2 --> 1.0 --> 1*5 ---> 5
		
		//마지막 버튼 번호
		int endPageBtnNo = (int)Math.ceil(crtPage/(double)pageBtnCount) * pageBtnCount; //ceil은 무조건 올려서 (소수점 윗자리) 정수값을 double형으로 만듦.
		
		//시작 버튼 번호
		int startPageBtnNo = endPageBtnNo - (pageBtnCount - 1); //마지막이 5면 5-1 빼주면 시작 번호
		
		//다음 버튼 boolean형 (글이 더 남아있지 않으면 안 보임)
		boolean next;
		
		if(endPageBtnNo * listCnt < totalCount) { //5 * 10 < 51 --> 다음 버튼 보여야 됨
			next = true;
		} else {
			next = false;
			//페이지당 5개인 거 맞추려고 제일 마지막에 빈 페이지가 남게 됨. 이거(125) 없애는 식 추가.
			endPageBtnNo = (int)Math.ceil(totalCount/(double)listCnt);
		}
		
		//이전 버튼
		boolean prev;
		
		if(startPageBtnNo != 1) {
			prev = true;
		} else {
			prev = false;
		}
		
		//bList prev startPageBtnNo endPageBtnNo next화면에 보내기
		Map<String, Object> pageMap = new HashMap<String, Object>();
		pageMap.put("bList", bList);
		pageMap.put("prev", prev);
		pageMap.put("startPageBtnNo", startPageBtnNo);
		pageMap.put("endPageBtnNo", endPageBtnNo);
		pageMap.put("next", next);
		
		
		//crtPage는 넘길 필요 없음
		return pageMap;
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
		
		/*페이징 만들기 전 게시글 대량 등록
		for(int i=1; i<=1234; i++) {
			bVo.setTitle(i + "번째 글 제목입니다.");
			bVo.setContent(i + "번째 글 내용입니다.");
			bDao.insert(bVo);
		}
		*/
		
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
