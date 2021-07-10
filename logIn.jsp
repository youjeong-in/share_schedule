<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 페이지</title>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap" rel="stylesheet">
<link href="resources/css/style.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script src ="js/js.js"> </script> 
</head>
<body>
<div class = "title">S스케줄 <i class="fa fa-refresh"></i></div>
   
  <p>
  <label>아이디</label>
  <input class="w3-input" name="userId" type="text"></p>

  <p>
  <label>비밀번호</label>
  <input class="w3-input" name ="userpwd" type="text"></p>


	<div class="click" onclick="logInInfo()">로그인</div>
	<div class="empty">or</div>

	<div class="empty" onclick="searchInfo()">비밀번호를 잊으셨나요?</div> 
	
	<div class="click2"><a href="http://192.168.219.104/signUp.jsp">회원가입</a></div>




</body>
</html>