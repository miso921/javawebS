<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>mailForm.jsp</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
	<jsp:include page="/WEB-INF/views/include/bs4.jsp" />
	<script>
		'use strict';
		
		function fCheck() {
			let emails = "";
			let email = document.getElementsByName("mailChk");
			for(let i=0; i<email.length; i++) {
				if(document.getElementsByName("mailChk")[i].checked == true) {
					emails += document.getElementsByName("mailChk")[i].value + ";";
				}
			}
			$("#toMail").val(emails);
			$('#myModal').modal('hide'); 
		}
	</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br /></p>
<div class="container">
	<h2>메 일 보 내 기</h2>
	<p>(받는 사람의 메일 주소를 정확히 입력하셔야 합니다!)</p>
	<pre>
		SMTP(Simple Mail Transfer Protocol)
		웹서버로 구축한게 아니기 때문에 구글이 지원해주는 메일 서비스 포트번호를 빌려서 메일 전송 연습할 예정!
		웹서버에서는 포트번호는 25번이고 보조 포트번호 587번으로 지원해준다. 
	</pre>
	<input type="button" value="주소록" data-toggle="modal" data-target="#myModal" class="btn btn-success mb-2" />
	<form name="myform" method="post">
		<table class="table table-bordered">
			<tr>
				<th>받는사람</th>
				<td>
					<input type="text" id="toMail" name="toMail" value="${email}" placeholder="받는사람 메일주소를 입력하세요." class="form-control" required autofocus />
				</td>
			</tr>
			<tr>
				<th>메일제목</th>
				<td>
					<input type="text" name="title" placeholder="메일 제목을 입력하세요!" class="form-control" required />
				</td>
			</tr>
			<tr>
				<th>메일내용</th>
				<td>
					<textarea rows="7" name="content" class="form-control" required></textarea>
				</td>
			</tr>
			<tr>
				<td colspan="2" class="text-center">
					<input type="submit" value="메일전송" class="btn btn-success mr-2" />
					<input type="reset" value="다시쓰기" class="btn btn-info mr-2" />
					<input type="button" value="돌아가기" onclick="location.href='${ctp}/';" class="btn btn-warning" />
				</td>
			</tr>
		</table>
	</form>
</div>

<!-- The Modal -->
<div class="modal" id="myModal">
  <div class="modal-dialog">
    <div class="modal-content">

      <!-- Modal Header -->
      <div class="modal-header">
        <h4 class="modal-title">주 소 록</h4>
        <button type="button" class="close" data-dismiss="modal">&times;</button>
      </div>

      <!-- Modal body -->
      <div class="modal-body">
	      <div class="row text-center">
			    <div class="col-2">번호</div>
			    <div class="col-4">성명</div>
			    <div class="col-5">이메일</div>
			    <div class="col-1"></div>
	      </div><hr />
      	<c:forEach var="vo" items="${vos}" varStatus="st">
	        <div class="row text-center">
		        <div class="col-2">${st.count}</div>
		        <div class="col-4">${vo.name}</div>
		        <div class="col-5">${vo.email}</div>
		        <div class="col-1"><input type="checkbox" value="${vo.email}" name="mailChk" /></div>
		      </div><hr />
      </c:forEach>
      <div class="text-center">
	      <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
	      <input type="button" value="확인" onclick="fCheck()" class="btn btn-success" />
	    </div>  
			</div>
			
      <!-- Modal footer -->
      <!-- <div class="modal-footer">
      </div> -->

    </div>
  </div>
</div>
<p><br /></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>