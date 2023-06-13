<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>aria.jsp</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<jsp:include page="/WEB-INF/views/include/bs4.jsp" />
	<script>
		'use strict';
		let str = '';
		let cnt = 0;
		
		function ariaCheck() {
			let pwd = document.getElementById("pwd").value;
			
			$.ajax({
				type : "post",
				url  : "${ctp}/study/password/aria",
				data : {pwd : pwd},
				success : function(res) {
					cnt++;
					str += cnt + ":" + res + "<br />";
					$("#demo").html(str);
				}
			});
		}
	</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br /></p>
<div class="container">
	<h2>ARIA</h2>
	<p>
		ARIA 암호화 방식은 경량환경 및 하드웨어 구현을 위해 최적화된 알고리즘으로, Involutional SPN 구조를 갖는 범용블록 암호화 알고리즘이다.
		ARIA가 사용하는 연산은 대부분 XOR와 같은 단순한 바이트단위연산으로, 블록크기는 128bit<b>(총비트수: 256bit = 32문자)</b>이다.<br />
		ARIA는 <b>Academy(학계), Research Institute(연구소), Agency(정부기관)의 첫 글자를 따서 만들었다.<br />
		ARIA암호화는 복호화가 가능</b>합니다.
	</p>
	<hr />
	<p>
		<input type="text" name="pwd" id="pwd" autofocus />
		<input type="button" value="ARIA암호화" onclick="ariaCheck()" class="btn btn-success" />
		<input type="button" value="다시하기" onclick="location.reload()" class="btn btn-primary" />
	</p>
	<hr />
	<div>
		<div>출력결과</div>
		<span id="demo"></span>
	</div>
</div>
<p><br /></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>