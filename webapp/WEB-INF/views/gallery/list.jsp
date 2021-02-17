<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<link href="${pageContext.request.contextPath }/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/assets/css/gallery.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/assets/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.12.4.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/bootstrap/js/bootstrap.js"></script>

</head>


<body>
	<div id="wrap">

		<c:import url="/WEB-INF/views/include/header.jsp"></c:import>
		<!-- //header -->
		<!-- //nav -->

		<c:import url="/WEB-INF/views/include/galleryAside.jsp"></c:import>
		<!-- //aside -->


		<div id="content">

			<div id="content-head">
				<h3>갤러리</h3>
				<div id="location">
					<ul>
						<li>홈</li>
						<li>갤러리</li>
						<li class="last">갤러리</li>
					</ul>
				</div>
				<div class="clear"></div>
			</div>
			<!-- //content-head -->


			<div id="gallery">
				<div id="list">
			
						<c:if test="${authUser != null }">
							<button id="btnImgUpload">이미지올리기</button>
							<div class="clear"></div>
						</c:if>
			
					<ul id="viewArea">
						
						<!-- 이미지반복영역 -->
						<c:forEach items="${galList }" var="vo">
							<!-- 이미지 no의 data-no를 다른 input 안에 넣으면 인식 안 됨 -->
							<li data-no="${vo.no }">
							
								<div class="view" >
									<img class="imgItem" src="${pageContext.request.contextPath }/upload/${vo.saveName}">
									<div class="imgWriter">작성자: <strong>${vo.userName}</strong></div>
								</div>

							</li>
						</c:forEach>
						<!-- 이미지반복영역 -->
						
						
					</ul>
				</div>
				<!-- //list -->
			</div>
			<!-- //board -->
		</div>
		<!-- //content  -->
		<div class="clear"></div>

		<c:import url="/WEB-INF/views/include/footer.jsp"></c:import>
		<!-- //footer -->

	</div>
	<!-- //wrap -->

	
		
	<!-- 이미지등록 팝업(모달)창 -->
	<div class="modal fade" id="addModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title">이미지등록</h4>
				</div>
				
				<!-- get으로 하면 뭔짓을 해도 MultipartException 오류 뜨는데 post로 하면 바로 됨. -->
				<!-- enctype 주의 -->
				<form method="post" action="${pageContext.request.contextPath }/gallery/upload" enctype="multipart/form-data" > 
					<div class="modal-body">
						<div class="form-group">
							<label class="form-text">글작성</label>
							<input id="addModalContent" type="text" name="content" value="" >
						</div>
						<div class="form-group">
							<label class="form-text">이미지선택</label>
							<input id="file" type="file" name="file" >
						</div>
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn" id="btnUpload">등록</button>
					</div>
				</form>
				
				
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	


	<!-- 이미지보기 팝업(모달)창 -->
	<div class="modal fade" id="viewModal">
		<div class="modal-dialog" >
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title">이미지보기</h4>
				</div>
				<div class="modal-body">
					
					<div class="formgroup" >
						<img id="viewModelImg" src =""> <!-- ajax로 처리 : 이미지출력 위치-->
					</div>
					
					<div class="formgroup">
						<p id="viewModelContent"></p>
					</div>
					
				</div>
				<form method="post" action="${pageContext.request.contextPath }/gallery/remove">
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
					<button type="button" class="btn btn-danger" id="btnDel">삭제</button>
					
				</div>
				
				<!-- 이미지의 no -->
				<input id="modalNo" type="text" name="galNo" value="">
				
				</form>
				
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->	


</body>

<script type="text/javascript">

	//이미지 등록 모달창
	$("#btnImgUpload").on("click", function(){
		console.log("이미지저장 모달창 호출");
		
		$("#addModal").modal();
		
	});
	
	//이미지 보기
	$("#viewArea").on("click", "li", function(){
		console.log("이미지보기 모달창 호출");
		
		var no = $(this).data("no"); //다른 태그말고 li에 data-no 추가
		$("#modalNo").val(no);
		console.log(no);
		
		
		//ajax방식으로 요청(보기)
		$.ajax({

			url : "${pageContext.request.contextPath }/gallery/read",		
			type : "post",
			//contentType : "application/json",
			data : {no: no},

			dataType : "json",
			success : function(galVo){ 
				console.log(galVo);
				
				var saveName = "${pageContext.request.contextPath }/upload/"+galVo.saveName;
				var content = galVo.content;
				
				//console.log(saveName);
				//console.log(content);
				
				//attr('속성', '속성값') : src="", alt="", title="", a태그의 href="" target="" 등의 값을 변경
				$("#viewModelImg").attr("src", saveName);
				$("#viewModelContent").text(content);
				
				//유저번호 비교해서 본인 글만 지울 수 있도록 추가
				
			},
			error : function(XHR, status, error) { //오류메세지 보려고 쓰는 거
				console.error(status + " : " + error);
			}
		});
		
		$("#viewModal").modal();
	});
	
	
	//삭제 (처음에 바로 #btnDel로 했는데 범위가 #modalNo까지 안 닿아서 인식을 못함)
	$("#viewModal").on("click", "#btnDel",function(){
		console.log("삭제버튼")
		
		var no = $("#modalNo").val();
		//var no = ("#modalNo").data("no");
		console.log(no);
		
		//ajax방식으로 요청(삭제)
		$.ajax({

			url : "${pageContext.request.contextPath }/gallery/remove",		
			type : "post",
			//contentType : "application/json",
			data : {no: no},

			dataType : "json",
			success : function(){ 

			},
			error : function(XHR, status, error) { //오류메세지 보려고 쓰는 거
				console.error(status + " : " + error);
			}
		});
		
		$("#viewModal").modal("hide");
		
	});

</script>




</html>

