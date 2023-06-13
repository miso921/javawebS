<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>uuidForm.jsp</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<jsp:include page="/WEB-INF/views/include/bs4.jsp" />
	<script>
		'use strict';
		let str = '';
		let cnt = 0;
		
		function uuidCheck() {
			$.ajax({
				type : "post",
				url  : "${ctp}/study/uuid/uuidForm",
				success : function(res) {
					cnt++;
					str += cnt + ":" + res + "<br />";
					$("#demo").html(str);
				},
				error : function() {
					alert("전송오류!");
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
	<h2>UUID에 대하여</h2>
	<pre>
		UUID(Univercal Unique Identifier)란, 네트워크상에서 고유성이 보장되는 id를 만들기 위한 규약
		32자리의 16진수(16x8=128Bit)로 표현된다.
		표시예 : 8-4-4-4-12 자리로 표현한다.
		예 : 52344erv-38t0-nw3y-5ejg-gjw32k3jbn47
	</pre>
	<hr />
	<p>
		<input type="button" value="UUID생성" onclick="uuidCheck()" class="btn btn-success" />
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