<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script type="text/javascript" src="/mysite5/assets/js/jquery/jquery-1.12.4.js"></script>

<link href="${pageContext.request.contextPath }/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/assets/css/guestbook.css" rel="stylesheet" type="text/css">

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
				
				<!-- ajax로 처리하는 방명록 출력 영역 -->
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

</body>

<script type="text/javascript">

	//////////////리스트 출력//////////////
	//DOM 생성(브라우저 준비가 끝났을 때) 
	$("document").ready(function(){
		console.log("ready");
		
		//데이터 보낼 건 없고 리스트 받기만 함
		$.ajax({
	
			url : "${pageContext.request.contextPath }/api/guest/list",		
			type : "post",
			//contentType : "application/json",
			//data : {name: name, password: password, content: content},
	
			dataType : "json",
			success : function(guestList){ 
				/*성공시 처리해야될 코드 작성*/
				console.log(guestList); //이대로 출력하면 배열 확인 가능. 자바스크립트로 보여주는 상태.
				
				for(var i=0; i<guestList.length; i++){
					render(guestList[i]);
				};
				
			},
			error : function(XHR, status, error) { //오류메세지 보려고 쓰는 거
				console.error(status + " : " + error);
			}
		});
		
	});

	//////////////방명록 등록//////////////
	$("#btnSubmit").on("click", function(){
		console.log("방명록 등록 버튼 클릭");
		
		//방명록 데이터 수집
		var name = $("[name='name']").val();
		var password = $("[name='password']").val();
		var content = $("[name='content']").val();
		console.log(name);
		console.log(password);
		console.log(content);
		
		//ajax방식으로 요청(저장)
		$.ajax({

			url : "${pageContext.request.contextPath }/api/guest/write",		
			type : "post",
			//contentType : "application/json",
			data : {name: name, password: password, content: content},

			dataType : "json",
			success : function(guestVo){ 
				//guestVo는 결과물이고 ajax로 보내고 등록된 데이터가 다시 올 때 화면에 나와야 되므로 이 안에 html이랑 같이 써야 됨
				/*성공시 처리해야될 코드 작성*/
				console.log(guestVo);
				render(guestVo); //방명록 정보 출력(실제 코드는 아래 따로 뺌)
				
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
	
	
	//방명록 글 정보 + hrml 조합해서(문자열로 만듦) 화면에 출력(jquery)
	function render(guestVo){
		var str = "";
		str += '<table class="guestRead">';
		str += '	<colgroup>';
		str += '		<col style="width: 10%;">';
		str += '		<col style="width: 40%;">';
		str += '		<col style="width: 40%;">';
		str += '		<col style="width: 10%;">';
		str += '	</colgroup>';
		str += '	<tr>';
		str += '		<td>' + guestVo.no + '</td>';
		str += '		<td>' + guestVo.name + '</td>';
		str += '		<td>' + guestVo.regDate + '</td>';
		str += '		<td><a href="${pageContext.request.contextPath }/guest/dform?no=guestVo.no">[삭제]</a></td>';
		str += '	</tr>';
		str += '	<tr>';
		str += '		<td colspan=4 class="text-left">' + guestVo.content + '</td>';
		str += '	</tr>';
		str += '</table>';
		
		$("#guestListArea").prepend(str); //html이면 방명록 글 하나가 바뀌기만 하니까 앞에 계속 추가하는 prepend로 함.
	}
	
	
</script>

</html>