<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap" rel="stylesheet">
<link href = "resources/css/logIn.css" rel = "stylesheet" type = "text/css"/>

<script src = "resources/js/js.js"></script>
<title>로그인</title>
</head>
<!-- <body onLoad="message('${message }')"> -->

<body onLoad="getAjax('https://api.ipify.org','format=json','setPublicIp')">

<a href="https://api.ipify.org?format=jsonp&callback=getIP" >ip조회</a>

<div id="main">
<div id="bigBox">
<div id="logo">ONE
<div class="logo2">로그인</div></div>

<div id="id">아이디</div>
<div><input type="text" id="idBox" class="box" name="userId" placeholder="아이디를 입력해주세요.">
</div>
<div id="pass">비밀번호</div>
<div><input type="password" id="passBox" class="box" name="userPass" placeholder="비밀번호를 입력해주세요."></div>
<div id="idForget">아이디를 잊으셨나요?</div>
<div id="text">ICIA 일보아카데미만 로그인 가능합니다! 게스트 계정 만들 수 없고, 1조 프로젝트 용 로그인 창 입니다.</div>

<div><div id="signUp"><a href="signUp" style="text-decoration:none; color:#5191ce;">회원가입</a>
<input type="button" class="button" name="next" value="다음" onClick="sendUserId()">
<input type="button" id="button2" class="button" name="subMit" value="로그인" onClick="logInInfo()" style="display:none;">

</div></div>



</div>
</div>

</body>
</html>