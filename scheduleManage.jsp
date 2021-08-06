<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="resources/js/js.js"></script>

<script src = "http://code.jquery.com/jquery-latest.js"></script>
<link href="resources/css/schedule.css" rel="stylesheet" type="text/css" />
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap"
	rel="stylesheet">
<title>schedule Manage</title>
<script>
	function backMain() {
		let logo = document.getElementById("logo");

		let form = makeForm("logIn", "get");

		form.appendChild(logo);

		document.body.appendChild(form);
		form.submit();
	}
	
	//새 스케줄 등록
	function newSd(){
		const main = document.getElementById("main");
		const upload = "<div class='upload'><div id='file'></div><div id='button' class='button' onClick='addSd()'>추가</div></div>";
		
		main.innerHTML = upload;
	}

	function addSd() {
		let fileSearch = document.getElementById("file");
		let filespace = "<div><input type='file' name='sdFile' class='sdFile' multiple required /><div id='send' class='button' onClick='sendFile()'>전송</div></div>";
		filespace += "<div id='delete' class='button' onClick='deleteBtn()'>삭제</div>";
		
		
		fileSearch.innerHTML = filespace;

	}
	
	function sendFile() {
		let file = document.getElementsByName('sdFile')[0];

		let f = makeMultipartForm('sendFile', 'post');
		f.appendChild(file);

		document.body.appendChild(f);
		f.submit();

	}

	function deleteBtn() {
		let sdFile = document.getElementsByName("sdFile")[0];
		let deletebtn = document.getElementById("delete");
		let sendbtn = document.getElementById("send");

		sdFile.remove();
		deletebtn.remove();
		sendbtn.remove();
	}
</script>
</head>
<body>
	<div id="header">
		<div id="logo" onClick="backMain()">O N E</div>
		<div id="inb">
			<div id="inner">
				<div class="cate">
					<div onclick="newSd()"><a> 새 스케줄 등록 </a></div>
				</div>
				<div class="cate">
					<a> 스케줄 보기</a>
				</div>
			</div>
		</div>
	</div>
	<div id="main">
				
		<div id="fileList"></div>
	</div>
</body>
</html>