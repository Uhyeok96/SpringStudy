<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>error_page.jsp : 에러페이지 advice</title>
</head>
<body>

	<h2>컨트롤러에서 예외처리 지원용 코드</h2>
	
	<h4><c:out value="${ exception.getMessage() }"></c:out></h4>
	
	<ul>
		<c:forEach items="${ exception.getStackTrace() }" var="stack">
			<li>
				<c:out value="${ stack }"></c:out>
			</li>
		</c:forEach>
	</ul>
	
</body>
</html>