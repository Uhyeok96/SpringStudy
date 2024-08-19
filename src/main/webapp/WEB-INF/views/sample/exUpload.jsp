<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>exUpload.jsp : commons-upload를 활용한 테스트</title>
</head>
<body>

	<h2>파일 업로드 테스트</h2>
	
	<form action="/sample/exUploadPost" method="post" enctype="multipart/form-data">
		<div><input type="file" name="files"></div>
		<div><input type="file" name="files"></div>
		<div><input type="file" name="files"></div>
		<div><input type="file" name="files"></div>
		<div><input type="file" name="files"></div>
		<div><input type="submit">전송</div>
	</form>
</body>
</html>