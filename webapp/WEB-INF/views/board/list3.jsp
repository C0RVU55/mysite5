<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="${pageContext.request.contextPath }/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">

</head>


<body>
	<div id="wrap">

		<c:import url="/WEB-INF/views/include/header.jsp"></c:import>
		<!-- header + navi -->

		<div id="aside">
			<h2>게시판</h2>
			<ul>
				<li><a href="${pageContext.request.contextPath }/board/list">일반게시판</a></li>
				<li><a href="${pageContext.request.contextPath }/board/rlist">댓글게시판</a></li>
			</ul>
		</div>
		<!-- //aside -->

		<div id="content">

			<div id="content-head">
				<h3>게시판</h3>
				<div id="location">
					<ul>
						<li>홈</li>
						<li>게시판</li>
						<li class="last">일반게시판</li>
					</ul>
				</div>
				<div class="clear"></div>
			</div>
			<!-- //content-head -->

			<div id="board">
				<div id="list">
					<form action="${pageContext.request.contextPath }/board/list3" method="get">
						<div class="form-group text-right">
							<input type="text" name="keyword">
							<button type="submit" id="btn_search">검색</button>
						</div>
					</form>
					<table >
						<thead>
							<tr>
								<th>번호</th>
								<th>제목</th>
								<th>글쓴이</th>
								<th>조회수</th>
								<th>작성일</th>
								<th>관리</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${pMap.bList}" var="vo">
								<tr>
									<td>${vo.no }</td>
									<td class="text-left"><a href="${pageContext.request.contextPath }/board/read?no=${vo.no}">${vo.title }</a></td>
									<td>${vo.name }</td>
									<td>${vo.hit }</td>
									<td>${vo.regDate }</td>
									<td>
										<c:if test="${vo.userNo == sessionScope.authUser.no }">
											<a href="${pageContext.request.contextPath }/board/remove?no=${vo.no}">[삭제]</a>
										</c:if>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
		
					<div id="paging"> <!-- 페이징 잘 보고 이해하기 -->
						<ul>
							<c:if test="${pMap.prev == true}">
								<li><a href="${pageContext.request.contextPath }/board/list3?crtPage=${pMap.startPageBtnNo-1}&keyword=${param.keyword}">◀</a></li>
							</c:if>
							
							<c:forEach begin="${pMap.startPageBtnNo }" end="${pMap.endPageBtnNo }" step="1" var="page"> <!-- step은 1번 돌 때 증가하는 정도 -->
								<li><a href="${pageContext.request.contextPath }/board/list3?crtPage=${page }&keyword=${param.keyword}">${page }</a></li>
								<!-- 검색했을 때 다른 페이지 넘기면 검색값이 사라지는 걸 방지하기 위해 키워드도 추가. -->
							</c:forEach>
							
							<c:if test="${pMap.next == true}">
								<li><a href="${pageContext.request.contextPath }/board/list3?crtPage=${pMap.endPageBtnNo+1}&keyword=${param.keyword}">▶</a></li>
							</c:if>
						</ul>
						
						
						<div class="clear"></div>
					</div>
					<c:if test="${sessionScope.authUser != null }">
						<a id="btn_write" href="${pageContext.request.contextPath }/board/wform">글쓰기</a>
					</c:if>
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

</body>

</html>
