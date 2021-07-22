<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="resources/css/signUp.css" rel="stylesheet" type="text/css" />
<script src="resources/js/js.js"></script>
<title>메인페이지</title>
<script>
function logOut(){
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
	
	let userId = document.getElementsByName("userId")[0];
	//let pubIp = document.getElementsByName("publicIp")[0];
	let method = makeInput("hidden" , "method" , -1);
	let pubIp = makeInput("hidden" , "publicIp" , publicIp);
	let privateIp = makeInput("hidden", "privateIp" , location.host);
	const brow = makeInput("hidden", "browser", result);

	if(confirm("로그아웃하시겠습니까?")){
		
		let form = makeForm("logOut", "post");
		
		form.appendChild(userId);
		form.appendChild(method);
		form.appendChild(pubIp);
		form.appendChild(privateIp);
		form.appendChild(brow);
		
		
		document.body.appendChild(form);
		form.submit();
	
	}else{
		
	}

}
</script>
</head>
<body onLoad="getAjax('https://api.ipify.org','format=json','setPublicIp')">
	<input type="hidden" value="${userId }" name="userId" />
	<input type="hidden" value="${browser }" name="browser" />
	<!--addObject로 가져온 녀석들 -->

	<div>환영합니다.${uName }님의 페이지입니다.</div>
	

	<div name="logOut" class="button" onClick="logOut()">로그아웃</div>
	
	<div name="teamManage" class="button" onClick="" ><a href ="scheduleManage">스케줄 관리</a></div>
	<div name="schedule" class="button" onClick="">팀관리</div>
</body>
</html>