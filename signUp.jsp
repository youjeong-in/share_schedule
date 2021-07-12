<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap" rel="stylesheet">

<link href = "resources/css/signUp.css" rel = "stylesheet" type = "text/css"/>
<title>회원가입</title>
</head>
<!-- <body onLoad="message('${message }')"> -->

<body>

<div id="main">
<div id="bigBox">
<div id="logo">ONE
<div class="logo2">회원가입</div></div>
<div id="id">아이디</div>
<div><input type="text" id="idBox" class="box" name="userId" placeholder="아이디를 입력해주세요.">
</div>
<div id="pass">비밀번호</div>
<div><input type="text" id="passBox" class="box" name="userpass" placeholder="비밀번호를 입력해주세요."></div>
<div><input type="text" id="passBox2"class="box" name="userpass" placeholder="비밀번호를 다시 입력해주세요."></div>

<div id="Phone">전화번호</div>
<div><input type="text" class="box" id="userPhone" name="userPhone" placeholder="-빼고 입력해주세요."></div>

<div class="name">
<div><input type="text" id="firstName" class="box" name="userName" placeholder="성">
<div><input type="text" id="lastName" class="box" name="userName" placeholder="이름"></div></div>
</div>


<div><div id="signUp"><a href="logIn" style="text-decoration:none; color:#5191ce;">로그인</a>
<input type="button" class="button" name="userpass" value="가입하기"></div></div>

</div>
</div>

</body>
</html>