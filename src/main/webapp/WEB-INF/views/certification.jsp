<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>인증 페이지</title>
<script src = "resources/js/js.js"></script>
<link href = "resources/css/authMail.css" rel = "stylesheet" type = "text/css"/>
<script>
	function mailAuth(){
	
	let tCode = document.getElementsByName("tCode")[0];
	
	let f = document.createElement("form");
	
	f.action = "authConfirm";
	f.method = "post";
	
	f.appendChild(tCode);
	
	document.body.appendChild(f);
	f.submit(0);
		
	}
</script>
</head>
<body>
	 <input type="hidden" name ="tCode" value="${tCode }" /> 
	
	<div class = "button" onClick="mailAuth()" >클릭하여 수락해주세요.</div>
</body>
</html>