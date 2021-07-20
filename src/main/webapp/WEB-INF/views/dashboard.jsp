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
	
	let userId = document.getElementsByName("userId")[0];
	//let pubIp = document.getElementsByName("publicIp")[0];
	let method = makeInput("hidden" , "method" , -1);
	let pubIp = makeInput("hidden" , "publicIp" , publicIp);
	let privateIp = makeInput("hidden", "privateIp" , location.host);
	let brow = document.getElementsByName("browser")[0];

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
</body>
</html>