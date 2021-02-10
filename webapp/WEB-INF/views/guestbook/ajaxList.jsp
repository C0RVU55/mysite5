<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<!-- 부트스트랩 위에 추가하고 자바스크립트는 아래로 옮김 -->
<link href="${pageContext.request.contextPath }/assets/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/assets/css/guestbook.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="/mysite5/assets/js/jquery/jquery-1.12.4.js"></script>
<script type="text/javascript" src="/mysite5/assets/bootstrap/js/bootstrap.js"></script> <!-- jquery를 이용하는 거라 밑에 써야 됨 -->

</head>

<body>
	<div id="wrap">

		<c:import url="/WEB-INF/views/include/header.jsp"></c:import>
		<!-- header + navi -->

		<div id="aside">
			<h2>방명록</h2>
			<ul>
				<li>일반방명록</li>
				<li>ajax방명록</li>
			</ul>
		</div>
		<!-- //aside -->

		<div id="content">
			
			<div id="content-head">
            	<h3>일반방명록</h3>
            	<div id="location">
            		<ul>
            			<li>홈</li>
            			<li>방명록</li>
            			<li class="last">일반방명록</li>
            		</ul>
            	</div>
                <div class="clear"></div>
            </div>
            <!-- //content-head -->

			<div id="guestbook">
				<!--  <form action="${pageContext.request.contextPath }/api/guest/write" method="post"> -->
				<!-- ajax로 데이터 주고받을 거라 form태그 제외함 -->
					<table id="guestAdd">
						<colgroup>
							<col style="width: 70px;">
							<col>
							<col style="width: 70px;">
							<col>
						</colgroup>
						<tbody>
							<tr>
								<th><label class="form-text" for="input-uname">이름</label></td>
								<td><input id="input-uname" type="text" name="name"></td>
								<th><label class="form-text" for="input-pass">패스워드</label></td>
								<td><input id="input-pass" type="password" name="password"></td>
							</tr>
							<tr>
								<td colspan="4"><textarea name="content" cols="72" rows="5"></textarea></td>
							</tr>
							<tr class="button-area">
								<td colspan="4"><button id="btnSubmit" type="submit">등록</button></td>
							</tr>
						</tbody>
						
					</table>
					<!-- //guestWrite -->
					<input type="hidden" name="action" value="add">
					
				<!-- </form> -->	
				
				<!-- ajax로 따로 처리함 -->
				<!-- 
				<c:forEach items="${gList}" var="vo">
					<table class="guestRead">
						<colgroup>
								<col style="width: 10%;">
								<col style="width: 40%;">
								<col style="width: 40%;">
								<col style="width: 10%;">
						</colgroup>
						<tr>
							<td>${vo.no }</td>
							<td>${vo.name }</td>
							<td>${vo.regDate }</td>
							<td><a href="${pageContext.request.contextPath }/guest/dform?no=${vo.no}">[삭제]</a></td>
						</tr>
						<tr>
							<td colspan=4 class="text-left">${vo.content }</td>
						</tr>
					</table>
				</c:forEach>
				 -->
				<!-- //guestRead -->
				
				<!-- *****ajax로 처리하는 방명록 출력 영역***** -->
				<div id="guestListArea">
					<!-- 방명록 출력 -->
				</div>
				
			</div>
			<!-- //guestbook -->
		</div>
		<!-- //content  -->
		<div class="clear"></div>
		
		<c:import url="/WEB-INF/views/include/footer.jsp"></c:import>
		<!-- //footer -->

	</div>
	<!-- //wrap -->
	
	<!-- 모달창 영역 (부트스트랩에서 복붙) 이 코드는 브라우저에서는 안 보이지만 html 밑에 숨어 있게 됨 -->
	<div class="modal fade" id="delModal">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title">방명록 삭제</h4>
	      </div>
	      <div class="modal-body">
	      	<label>비밀번호</label>
	      	<input id="modalPassword" type="text" name="password" value=""><br>
	      	
	      	<!-- no 히든 처리 (no값은 삭제버튼 클릭할 때 들어가게 만듦) -->
	      	<input id="modalNo" type="text" name="no" value="">
	        
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
	        <button id="modalBtnDel" type="button" class="btn btn-primary">삭제</button>
	      </div>
	    </div><!-- /.modal-content -->
	  </div><!-- /.modal-dialog -->
	</div><!-- /.modal -->

</body>

<script type="text/javascript">

	//////////////리스트 출력//////////////
	//DOM 생성(브라우저 준비가 끝났을 때) 
	$("document").ready(function(){
		console.log("ready");
		
		//전체 리스트 출력 함수(복잡해지면 함수는 아래 모아두고 위에서는 이렇게만 실행 가능)
		fetchList();
		
	});
	
	//////////////[삭제] 클릭//////////////
	//삭제 a태그에 따로 클래스 줘서 만들기보단 부모<div>한테 줘서 자식<a>한테 위임하는 게 좋음
	$("#guestListArea").on("click", "a", function(){
		
		event.preventDefault(); //태그의 기본기능을 안 쓰고 싶을 때
		//console.log("부모클릭");
		console.log("자식클릭");
		console.log("모달창 호출");
		
		//숨긴 데이터 no 넣기
		var no = $(this).data("no");
		$("#modalNo").val(no);
		console.log(no);
		
		/* this값을 이렇게 받기도 함
		var $that = $(this);
		console.log($that);
		*/
		
		//모달창 뜰 때 비번 필드 초기화 (no초기화 안 되게 주의)
		$("#modalPassword").val("");
		
		//모달창 호출
		$("#delModal").modal();
		
	});
	
	//////////////모달창 삭제버튼 클릭//////////////
	//삭제 프로세스 진행
	$("#modalBtnDel").on("click", function(){
		console.log("모달창 삭제버튼 클릭");
		
		//모달창 비번, no 수집
		/*
		var password = $("#modalPassword").val();
		var no = $("#modalNo").val();
		
		console.log(password);
		console.log(no);
		*/
		
		var no = $("#modalNo").val();
		
		//모달창 비번, no 수집 --> 파라미터값을 객체로 모아두면 아래 data에 변수 no: no 이렇게 일일이 다 안 쓰고 객체만 넣으면 됨
		var guestVo = {
			password: $("#modalPassword").val(),
			no: $("#modalNo").val()
		};
		console.log(guestVo);
		
		//ajax방식으로 삭제
		$.ajax({

			url : "${pageContext.request.contextPath }/api/guest/remove",		
			type : "post",
			//contentType : "application/json",
			data : guestVo,

			dataType : "json",
			success : function(count){ 
				/*성공시 처리해야될 코드 작성*/
				console.log(count);
				
				if(count == 1){ //count == 1 --> 삭제 작업
					
					$("#delModal").modal("hide"); //모달창 닫기
					
					$("#t-"+no).remove(); //화면에서 글 안 보이게 하기
					
				} else { //count == 0 --> 경고창
					alert("비밀번호가 틀렸습니다.");
				
					$("#modalPassword").val(""); //비번창 초기화
				}
				
			},
			error : function(XHR, status, error) { 
				console.error(status + " : " + error);
			}
		});
		
	});

	//////////////방명록 등록//////////////
	$("#btnSubmit").on("click", function(){
		console.log("방명록 등록 버튼 클릭");
		
		//방명록 데이터 수집
		/*
		var name = $("[name='name']").val();
		var password = $("[name='password']").val();
		var content = $("[name='content']").val();
		console.log(name);
		console.log(password);
		console.log(content);
		*/
		
		//등록도 삭제와 마찬가지로 파라미터값들을 객체로 묶음
		var guestVo = {
			name: $("[name='name']").val(),
			password: $("[name='password']").val(),
			content: $("[name='content']").val()
		};
		console.log(guestVo);
	
		//ajax방식으로 요청(저장)
		$.ajax({

			url : "${pageContext.request.contextPath }/api/guest/write2",		
			type : "post",
			
			/* (1)write()에 파라미터로 보내는 방식
			//contentType : "application/json",
			data : guestVo,
			*/
			
			//(2)write2()에 json 써서 데이터를 바디영역으로 보내는 방식
			contentType : "application/json", 
			data : JSON.stringify(guestVo),
			//application/json (보내는 데이터가 json이라는 뜻) --> 위 객체(파라미터값 모음)을 json으로 만들어서 파라미터가 아니라 본문 내용처럼 보냄
			//--> JSON.stringify(변환하고 싶은거) write는 디스패쳐서블릿인데 파라미터가 아닌 걸 보내서 아래처럼 데이터를 못 받음
			//GuestVo [no=0, name=null, password=null, content=null, regDate=null]
			//--> 바디영역으로 전달한 데이터를 @RequestBody로 받는 write2을 씀 --> GuestVo [no=0, name=alalal, password=565, content=alalal, regDate=null]

			dataType : "json",
			success : function(guestVo){ 
				//guestVo는 결과물이고 ajax로 보내고 등록된 데이터가 다시 올 때 화면에 나와야 되므로 이 안에 html이랑 같이 써야 됨 --> html은 다른 함수로 뺌
				/*성공시 처리해야될 코드 작성*/
				console.log(guestVo);
				render(guestVo, "up"); //방명록 정보 출력(실제 코드는 아래 따로 뺌)
				
				//입력폼 비우기
				$("[name='name']").val("");
				$("[name='password']").val("");
				$("[name='content']").val("");
				
			},
			error : function(XHR, status, error) { //오류메세지 보려고 쓰는 거
				console.error(status + " : " + error);
			}
		});

	});
	
	/*
	//json형식 객체
	{"no":11, "name":"이다현"}, "content":"안녕", "regDate":"2021-02-09"}
	
	//json형식 리스트
	[
		{"no":11, "name":"이다현"}, "content":"안녕", "regDate":"2021-02-09"},
		{"no":12, "name":"2다현"}, "content":"안녕2", "regDate":"2021-02-09"}
	]
	*/
	
	
	//방명록 글 정보 + hrml 조합해서(문자열로 만듦) 화면에 출력(jquery) (삭제쪽 따옴표 주의, data-다음은 소문자만 가능)
	function render(guestVo, updown){
		var str = '';
		str += '<table id="t-'+guestVo.no+'" class="guestRead">';
		str += '	<colgroup>';
		str += '		<col style="width: 10%;">';
		str += '		<col style="width: 40%;">';
		str += '		<col style="width: 40%;">';
		str += '		<col style="width: 10%;">';
		str += '	</colgroup>';
		str += '	<tr>';
		str += '		<td>'+guestVo.no+'</td>';
		str += '		<td>'+guestVo.name+'</td>';
		str += '		<td>'+guestVo.regDate+'</td>';
		str += '		<td><a href="" data-no="'+guestVo.no+'">[삭제]</a></td>';
		str += '	</tr>';
		str += '	<tr>';
		str += '		<td colspan=4 class="text-left">'+guestVo.content+'</td>';
		str += '	</tr>';
		str += '</table>';
		
		//정렬 옵션 추가
		if(updown == "down"){
			$("#guestListArea").append(str); //여기 아이디가 guestBookListArea였어서 아예 브라우저에 출력이 안 됐었음.
		} else if(updown == "up") {
			$("#guestListArea").prepend(str); //html이면 방명록 글 하나가 바뀌기만 하니까 앞에 계속 추가하는 prepend로 함.
		} else {
			console.log("정렬 미지정");
		}
		
	}
	
	//전체 리스트 출력 함수
	function fetchList(){
		
		//데이터 보낼 건 없고 리스트 받기만 함
		$.ajax({
	
			url : "${pageContext.request.contextPath }/api/guest/ajaxList",		
			type : "post",
			//contentType : "application/json",
			//data : {name: name, password: password, content: content},
	
			dataType : "json",
			success : function(guestList){ 
				/*성공시 처리해야될 코드 작성*/
				console.log(guestList); //이대로 출력하면 배열 확인 가능. 자바스크립트로 보여주는 상태.
				
				for(var i=0; i<guestList.length; i++){
					//출력 안 됐을 때 여기에 console.log 넣어서 함수에 잘 도착하나 확인하면서 어디가 오류인지 찾아야 됨.
					render(guestList[i], "down");
				};
				
			},
			error : function(XHR, status, error) { 
				console.error(status + " : " + error);
			}
		});
		
	}
	
	
</script>

</html>