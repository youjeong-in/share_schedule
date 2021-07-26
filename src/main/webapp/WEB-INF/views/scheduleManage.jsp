<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src = "resources/js/js.js"></script>
<link href="resources/css/schedule.css" rel="stylesheet" type="text/css" />
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap" rel="stylesheet">
<title>schedule Manage</title>
<script>
	function backMain(){
		let logo = document.getElementById("logo");
		
		
		let form = makeForm("logIn","get");
		
		form.appendChild(logo);
		
		document.body.appendChild(form);
		form.submit();
	}
	
</script>
</head>
<body>
<div id ="header">
<div id="logo" onClick="backMain()"> O N E </div>
	<div id="inb">
	<div id="inner">
	<div class = "cate"><a> 새 스케줄 등록 </a></div>
 	<div class = "cate"><a> 스케줄 보기</a></div>
 	</div>
 	</div>
	</div>
<div id ="main" >
메인페이지
</div>
</body>
</html>