<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap" rel="stylesheet">
<link href = "resources/css/idForget.css" rel = "stylesheet" type = "text/css"/>
<title>아이디,비밀번호 찾기</title>

<script>
	const message = "${message}";
	
	if(message!=""){
		alert(message);
	}
</script>
</head>
<body message="${message }">
<div id="main">
	<div id="bigBox">
	 	<div id="logo">ONE</div>
	 <div id="smailBox">
	 	<form action="findId" method="post">
 			<div id="title">아이디 찾기</div>
 			<input type="text" name="userName" class="box" placeholder="이름을 입력하세요.">
 			<input type="text" name="userMail" class="box" placeholder="회원가입시 입력한 이메일을 입력하세요.">
 			<input type="submit" value="인증번호 받기" name="submitInfo" class="button">
 		</form>
	</div>
 
    <div id="smailBox">
    <form action="findPwd" method="post">
 		<div id="title">비밀번호 찾기</div>
 		<input type="text" name="userId" class="box" placeholder="아이디를 입력하세요.">
 		<input type="text" name="userMail" class="box" placeholder="회원가입시 입력한 이메일을 입력하세요.">
 		<input type="submit" value="인증번호 받기" name="submitInfo" class="button">
 	</form>
 	</div>
 		<a href="logIn" style="text-decoration: none; color: #5191ce; margin-left:35px;">로그인</a>
 	</div>
 	  
 </div>

</body>
</html>