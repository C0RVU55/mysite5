<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script type="text/javascript" src="/mysite5/assets/js/jquery/jquery-1.12.4.js"></script>

<link href="${pageContext.request.contextPath }/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/assets/css/user.css" rel="stylesheet" type="text/css">

</head>

<body>
	<div id="wrap">

		<c:import url="/WEB-INF/views/include/header.jsp"></c:import>
		<!-- header + navi -->

		<div id="aside">
			<h2>회원</h2>
			<ul>
				<li>회원정보</li>
				<li>로그인</li>
				<li>회원가입</li>
			</ul>
		</div>
		<!-- //aside -->

		<div id="content">
			
			<div id="content-head">
            	<h3>회원가입</h3>
            	<div id="location">
            		<ul>
            			<li>홈</li>
            			<li>회원</li>
            			<li class="last">회원가입</li>
            		</ul>
            	</div>
                <div class="clear"></div>
            </div>
            <!-- //content-head -->

			<div id="user">
				<div id="joinForm">
					<form id="joinForm" action="${pageContext.request.contextPath }/user/join" method="get">

						<!-- 아이디 -->
						<div class="form-group">
							<label class="form-text" for="input-uid">아이디</label> 
							<input type="text" id="input-uid" name="id" value="" placeholder="아이디를 입력하세요">
							<button type="button" id="btnCheck">중복체크</button>
						</div>
						
						<p id="msg">
						<!-- 이제 이렇게 안 하고 아래 jquery로 처리함
							<c:if test="${param.result eq 'pass' }">
								사용할 수 있는 id입니다.
							</c:if>
							<c:if test="${param.result eq 'fail' }">
								사용할 수 없는 id입니다.
							</c:if>
						-->
						</p>

						<!-- 비밀번호 -->
						<div class="form-group">
							<label class="form-text" for="input-pass">패스워드</label> 
							<input type="text" id="input-pass" name="password" value="" placeholder="비밀번호를 입력하세요"	>
						</div>

						<!-- 이메일 -->
						<div class="form-group">
							<label class="form-text" for="input-name">이름</label> 
							<input type="text" id="input-name" name="name" value="" placeholder="이름을 입력하세요">
						</div>

						<!-- //나이 -->
						<div class="form-group">
							<span class="form-text">성별</span> 
							
							<label for="rdo-male">남</label> 
							<input type="radio" id="rdo-male" name="gender" value="M" > 
							
							<label for="rdo-female">여</label> 
							<input type="radio" id="rdo-female" name="gender" value="F" > 

						</div>

						<!-- 약관동의 -->
						<div class="form-group">
							<span class="form-text">약관동의</span> 
							
							<input type="checkbox" id="chk-agree" value="" name="" >
							<label for="chk-agree">서비스 약관에 동의합니다.</label> 
						</div>
						
						<!-- 버튼영역 -->
		                <div class="button-area">
		                    <button type="submit" id="btn-submit">회원가입</button>
		                </div>
						
					</form>
				</div>
				<!-- //joinForm -->
			</div>
			<!-- //user -->
		</div>
		<!-- //content  -->
		<div class="clear"></div>
		
		<c:import url="/WEB-INF/views/include/footer.jsp"></c:import>
		<!-- //footer -->

	</div>
	<!-- //wrap -->

</body>

<script type="text/javascript">

	//괄호 주의
	//*****회원가입 아이디 중복체크*****
	$("#btnCheck").on("click", function(){
		
		//var uid = $("[name='id']").val();
		var uid = $("#input-uid").val();
		console.log(uid);
		
		//var pw = $("#input-pass").val();
		//console.log(uid+", "+pw);
		
		//ajax(서버와 통신하는 기술)데이터만 받기. 아래는 자바스크립트 객체.
		//get이든 post든 파라미터값을 받아도 주소창에 안 보이는 게 기본임.
		
		$.ajax({
			
			//파라미터에 값을 다 쓰는 게 아니라 위에서 받아서 변수만 넣음. 근데 파라미터가 많으면 다 못 쓰니까 data에 키:값 형태로 묶어서 넣음
			url : "${pageContext.request.contextPath }/user/idcheck",		
			type : "post",
			//contentType : "application/json",
			data : {id: uid},

			dataType : "text",
			success : function(result){ //컨트롤러에서 보낸 result값을 콘솔에 출력해서 확인
				/*성공시 처리해야될 코드 작성*/
				
				if(result == "pass"){
					console.log("사용할 수 있는 아이디");
					$("#msg").html("사용할 수 있는 아이디입니다"); //css적용 가능
					
				} else {
					console.log("사용할 수 없는 아이디");
					$("#msg").html("사용할 수 없는 아이디입니다");
				}
				
			},
			error : function(XHR, status, error) { //오류메세지 보려고 쓰는 거
				console.error(status + " : " + error);
			}
		});

	});

	
	//*****비밀번호 조건 및 약관동의 체크*****
	//폼을 submit할 때 --> submit되기 전에 들렀다 감
	$("#joinForm").on("submit", function(){
		
		//값 준비하는 건 위에 같이 모아두기
		//비번 체크 준비
		var pw = $("#input-pass").val();
		console.log(pw.length);
		
		//약관동의 체크 준비
		var check = $("#chk-agree").is(":checked"); 
		console.log(check);
		//$("input:checkbox[id='ID']").is(":checked") == true : false 체크박스 검색하면 나오는 거
		
		/////
		
		//비번 체크 (원래 이 코드가 약관동의 체크 아래 있었는데 테스트 순서 안 맞아서 위치 옮김)
		if(pw.length < 8){
			alert("비밀번호는 8자 이상이어야 합니다.");
			return false;
		};
		
		//약관동의 체크(check가 flase면 = true의 반대면)
		if(!check){
			alert("약관에 동의해 주세요.");
			return false; 
		};
		
		/*
		//약관동의
		if(check==true){ 
			//동의하기 checkbox 체크됨 --> submit
			return true;
		
		} else { 
			//체크 안 됨 --> alert창 띄우고 "약관에 동의해주세요" 출력 --> submit 안 됨
			alert("약관에 동의해 주세요.");
			return false; 
		}
		*/

		//return false; 테스트 --> 위에 거 체크하고 멈춤. 실제로 submit 안 됨.
		
		
		//위에 거 순서대로 다 통과했으면
		return true;
	});
	
	
</script>

</html>