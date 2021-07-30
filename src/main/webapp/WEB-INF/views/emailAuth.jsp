<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- <meta http-equiv="refresh" content="5; url=http://192.168.1.189/logIn"> -->
<script src = "resources/js/js.js"></script>
<link href = "resources/css/authMail.css" rel = "stylesheet" type = "text/css"/>
<title> 인증 페이지</title>
<script>
	function mailAuth(){
	
	
	
	const browser = navigator.userAgent.toLowerCase();
	let result = "";
	
	if(browser.lastIndexOf('edg')>-1){
		result = 'edge';
	}else if(browser.lastIndexOf('whale')>-1){
		result = 'whale';
	}else if(browser.indexOf('chrome')>-1){
		result = 'chrome';
	}else{
		result = 'any';
	}
	
	let tCode = document.getElementsByName("tCode")[0];
	const id = document.getElementsByName("userId")[0];
	const pwd =document.getElementsByName("userPass")[0];
	let method = makeInput("hidden" , "method" , 1);
	let pubIp = makeInput("hidden" , "publicIp" , publicIp);
	let privateIp = makeInput("hidden", "privateIp" , location.host);
	const brow = makeInput("hidden", "browser", result);


	
	let form = makeForm("linkAccess","post");
	
	form.appendChild(id);
	form.appendChild(pwd);
	form.appendChild(method);
	form.appendChild(pubIp);
	form.appendChild(privateIp);
	form.appendChild(brow);
	form.appendChild(tCode);
	
	
	document.body.appendChild(form);
	form.submit();
		
	}
</script>

</head>
<body onLoad="getAjax('https://api.ipify.org','format=json','setPublicIp')">

<div id="main">
<div id="bigBox">
<div id="logo">ONE
<div class="logo2">로그인</div></div>
 <input type="hidden" name ="tCode" value="${tCode }" /> 
<div id="id">아이디</div>
<div><input type="text" id="idBox" class="box" name="userId"  placeholder="아이디를 입력해주세요." onkeyup="enterId()">
</div>
<div id="pass">비밀번호</div>
<div><input type="password" id="passBox" class="box" name="userPass" placeholder="비밀번호를 입력해주세요." onkeyup="enterPwd()"></div>
<div id="idForget">아이디를 잊으셨나요?</div>
<div id="text">ICIA 일보아카데미만 로그인 가능합니다! 게스트 계정 만들 수 없고, 1조 프로젝트 용 로그인 창 입니다.</div>

<div><div id="signUp"><a href="signUpForm" style="text-decoration:none; color:#5191ce;">회원가입</a>
<input type="button" class="button" name="next" value="다음" onClick="sendUserId()">
<input type="button" id="button2" class="button" name="subMit" value="인증" onClick="mailAuth()" style="display:none;">


</div></div>



</div>
</div>

</body>
</html>