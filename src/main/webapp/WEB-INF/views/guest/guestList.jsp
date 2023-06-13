<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<% pageContext.setAttribute("newLine", "\n"); %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>guestList(블록페이징처리-한페이지분량조정하기-부트스트랩페이지네이션적용).jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp" />
  <style>
    th {
      background-color:#eee;
      text-align: center;
    }
  </style>
  <script>
    'use strict';
    
    function pageCheck() {
    	let pageSize = document.getElementById("pageSize").value;
    	location.href = "${ctp}/guest/guestList?pag=${pageVo.pag}&pageSize="+pageSize;
    }
    
    function delCheck(idx) {
    	let ans = confirm("현재 게시물을 삭제하시겠습니까?");
    	if(ans) location.href = "${ctp}/guest/guestDelete?idx="+idx;
    }
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <h2 class="text-center">방명록리스트</h2>
  <table class="table table-borderless mb-0 p-0">
    <tr>
      <td>
        <c:if test="${sAdmin != 'adminOk'}"><a href="${ctp}/guest/adminLogin" class="btn btn-success btn-sm">관리자</a></c:if>
        <c:if test="${sAdmin == 'adminOk'}"><a href="${ctp}/guest/adminLogout" class="btn btn-danger btn-sm">관리자로그아웃</a></c:if>
      </td>
      <td style="text-align:right;"><a href="${ctp}/guest/guestInput" class="btn btn-primary btn-sm">글쓰기</a></td>
    </tr>
  </table>
  <table class="table table-borderless mb-0 p-0">
    <tr>
      <td>
        <select name="pageSize" id="pageSize" onchange="pageCheck()">
          <option <c:if test="${pageVo.pageSize == 3}">selected</c:if>>3</option>
          <option <c:if test="${pageVo.pageSize == 5}">selected</c:if>>5</option>
          <option <c:if test="${pageVo.pageSize == 10}">selected</c:if>>10</option>
          <option <c:if test="${pageVo.pageSize == 15}">selected</c:if>>15</option>
          <option <c:if test="${pageVo.pageSize == 20}">selected</c:if>>20</option>
        </select> 건
      </td>
      <td class="text-right">
        <!-- 첫페이지 / 이전페이지 / (현재페이지번호/총페이지수) / 다음페이지 / 마지막페이지 -->
        <c:if test="${pageVo.pag > 1}">
          <a href="${ctp}/guest/guestList?pageSize=${pageVo.pageSize}&pag=1" title="첫페이지로">◁◁</a>
          <a href="${ctp}/guest/guestList?pageSize=${pageVo.pageSize}&pag=${pageVo.pag-1}" title="이전페이지로">◀</a>
        </c:if>
        ${pageVo.pag}/${pageVo.totPage}
        <c:if test="${pageVo.pag < pageVo.totPage}">
          <a href="${ctp}/guest/guestList?pageSize=${pageVo.pageSize}&pag=${pageVo.pag+1}" title="다음페이지로">▶</a>
          <a href="${ctp}/guest/guestList?pageSize=${pageVo.pageSize}&pag=${pageVo.totPage}" title="마지막페이지로">▷▷</a>
        </c:if>
      </td>
    </tr>
  </table>
  <c:set var="curScrStartNo" value="${pageVo.curScrStartNo}" />
  <c:forEach var="vo" items="${vos}" varStatus="st">
	  <table class="table table-borderless mb-0 mt-0">
	    <tr>
	      <td>
	        번호 : ${curScrStartNo} <!-- pageVo에 넘겼기 때문에 변수로 받아서 출력해야함!(73번 라인) -->
	        <c:if test="${sAdmin == 'adminOk'}">
	        	<a href="javascript:delCheck(${vo.idx})" class="btn btn-danger btn-sm">삭제</a>
	        </c:if>
	      </td>
	      <td style="text-align:right;">방문IP : ${vo.hostIp}</td>
	    </tr>
	  </table>
	  <table class="table table-bordered mt-0">
	    <tr>
	      <th style="20%">성명</th>
	      <td style="25%">${vo.name}</td>
	      <th style="20%">방문일자</th>
	      <td style="35%">${fn:substring(vo.visitDate,0,19)}</td>
	    </tr>
	    <tr>
	      <th>메일주소</th>
	      <td colspan="3">
	        <c:if test="${empty vo.email || fn:length(vo.email)<5 || fn:indexOf(vo.email,'@')==-1 || fn:indexOf(vo.email,'.')==-1}">- 없음 -</c:if>
	        <c:if test="${!empty vo.email && fn:length(vo.email)>=5 && fn:indexOf(vo.email,'@')!=-1 && fn:indexOf(vo.email,'.')!=-1}">${vo.email}</c:if>
	      </td>
	    </tr>
	    <tr>
	      <th>홈페이지</th>
	      <td colspan="3">
	        <c:if test="${empty vo.homePage || fn:length(vo.homePage)<10 || fn:indexOf(vo.homePage,'.')==-1}">- 없음 -</c:if>
	        <c:if test="${!empty vo.homePage && fn:length(vo.homePage)>=10 && fn:indexOf(vo.homePage,'.')!=-1}">${vo.homePage}</c:if>
	      </td>
	    </tr>
	    <tr>
	      <th>방문소감</th>
	      <td colspan="3" style="height:150px">${fn:replace(vo.content, newLine, '<br/>')}</td>
	    </tr>
	  </table>
	  <br/>
	  <c:set var="curScrStartNo" value="${curScrStartNo - 1}"/>
  </c:forEach>
  <br/>			<!-- 4페이지(1블록)에서 0블록으로 가게되면 현재페이지는 1페이지가 블록의 시작페이지가 된다. -->
  <!-- 첫페이지 / 이전블록 / 1(4) 2(5) 3 / 다음블록 / 마지막페이지 -->
  <div class="text-center">
	  <ul class="pagination justify-content-center">
	    <c:if test="${pageVo.pag > 1}"><li class="page-item"><a class="page-link text-secondary" href="${ctp}/guest/guestList?pageSize=${pageVo.pageSize}&pag=1">첫페이지</a></li></c:if>
	    <c:if test="${pageVo.curBlock > 0}"><li class="page-item"><a class="page-link text-secondary" href="${ctp}/guest/guestList?pageSize=${pageVo.pageSize}&pag=${(pageVo.curBlock-1)*pageVo.blockSize + 1}">이전블록</a></li></c:if>
	    <c:forEach var="i" begin="${pageVo.curBlock*pageVo.blockSize + 1}" end="${pageVo.curBlock*pageVo.blockSize + pageVo.blockSize}" varStatus="st">
	      <c:if test="${i <= pageVo.totPage && i == pageVo.pag}"><li class="page-item active"><a class="page-link text-white bg-secondary border-secondary" href="${ctp}/guest/guestList?pageSize=${pageVo.pageSize}&pag=${i}">${i}</a></li></c:if>
	      <c:if test="${i <= pageVo.totPage && i != pageVo.pag}"><li class="page-item"><a class="page-link text-secondary" href="${ctp}/guest/guestList?pageSize=${pageVo.pageSize}&pag=${i}">${i}</a></li></c:if>
	    </c:forEach>
	    <c:if test="${pageVo.curBlock < pageVo.lastBlock}"><li class="page-item"><a class="page-link text-secondary" href="${ctp}/guest/guestList?pageSize=${pageVo.pageSize}&pag=${(pageVo.curBlock+1)*pageVo.blockSize + 1}">다음블록</a></li></c:if>
	    <c:if test="${pageVo.pag < pageVo.totPage}"><li class="page-item"><a class="page-link text-secondary" href="${ctp}/guest/guestList?pageSize=${pageVo.pageSize}&pag=${pageVo.totPage}">마지막페이지</a></li></c:if>
	  </ul>
  </div>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>