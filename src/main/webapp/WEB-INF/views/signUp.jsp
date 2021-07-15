<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap"
	rel="stylesheet">
<link href="resources/css/signUp.css" rel="stylesheet" type="text/css" />
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="resources/js/js.js"></script>
<script>
	//중복체크
	function isDup(obj) {
		const userId = document.getElementsByName("userId")[0];

		if (obj.value != "재입력") {
			if (charCount(userId.value, 8, 12)) {
				if (!isValidateCheck(1, userId.value)) { //패턴에 합하지않는다면,
					alert("아이디형식이 맞지않습니다.");
					userId.value = "";
					userId.focus();
					return;
				}

				//ajax true면 사용가능
				postAjax("isDup", "userId=" + userId.value, "afterDup");
			} else {
				alert("아이디의 길이는 8~12이내 입니다.");
				userId.focus();
			}

		} else {
			userId.value = "";
			userId.readOnly = false;
			userId.focus();
		}

	}

	$(document).ready(function() {
		$("#idBox").blur(function() {
			var idBox = $('#idBox').value();
			postAjax("idDup", "userId=" + idBox, "afterDup");
		});
	});

	function afterDup(jsonData) {
		let btn = document.getElementById("dupBtn")
		let userId = document.getElementsByName("userId")[0];
		let msg = document.getElementById("msg");
		let dupId = document.getElementById("dupId");
		let idBox = document.getElementById("idBox");

		if (isValidateCheck(1, idBox)) {
			idBox.style.border = "1px solid #FF4646";
			dupId.innerText = "숫자와 영문 대소문자 8~12자리로 입력해주세요.";
			dupId.style.color = "#FF4646";
			idBox.value = "";
			idBox.focus();
			return;
		}

		//메세지가 turn일경우 사용가능
		if (jsonData == true) {
			btn.setAttribute("value", "재입력");
			userId.setAttribute("readonly", true);
			dupId.innerText = "사용가능한 아이디입니다.";
			idBox.style.border = "1px solid #51FFA6";
			dupId.style.color = "#51FFA6";

		} else {
			userId.value = ""
			dupId.innerText = "중복된 아이디가 있습니다.";
			idBox.style.border = "1px solid #FF4646";
			dupId.style.color = "#FF4646";
			userId.focus();
		}
	}

	function pwdCh(obj) {
		if (charCount(obj.value, 8, 20)) {
			if (!isValidateCheck(2, obj.value)) {
				alert("비밀번호는 8~20글자이내 숫자,대문자,소문자,특수문자의 3가지이상의 조합입니다.");
				obj.value = "";
				obj.focus();
			}
		} else {
			alert("비밀번호는 8~20이내로 입력해주세요.");
			obj.value = "";
			obj.focus();
		}
	}

	function pwdCh2(obj) {
		const pwd1 = document.getElementsByName("userPass")[0];
		const pwd2 = document.getElementsByName("userPass")[1];

		if (pwd1.value != pwd2.value) {
			alert("위의 패스워드와 일치하지 않습니다. 다시 확인해주세요");
			pwd1.value = "";
			pwd2.value = "";
			pwd1.focus();
		}
	}
</script>
<title>회원가입</title>
</head>
<!-- <body onLoad="message('${message }')"> -->

<body>

	<div id="main">
		<div id="bigBox">
			<div id="logo">
				ONE
				<div class="logo2">회원가입</div>
			</div>
			<div id="id">아이디</div>
			<div>
				<input type="text" id="idBox" class="box" name="userId"
					placeholder="*아이디를 입력해주세요." onkeyup="korCheck(this, event)">
				<div id="dupId"></div>
				<input type="button" class="button" id="dupBtn" value="중복체크"
					onClick="isDup(this)" />
			</div>
			<div id="pass">비밀번호</div>
			<div>
				<input type="password" id="passBox" class="box" name="userPass"
					placeholder="비밀번호를 입력해주세요." onBlur="pwdCh(this)">
			</div>
			<div>
				<input type="password" id="passBox2" class="box" name="userPass"
					placeholder="비밀번호를 다시 입력해주세요." onBlur="pwdCh2(this)">
			</div>

			<div id="Phone">전화번호</div>
			<div>
				<input type="text" class="box" id="userPhone" name="userPhone"
					placeholder="-빼고 입력해주세요.">
			</div>

			<div class="name">
				<div>
					<input type="text" id=name " class="box" name="userName"
						placeholder="이름">
				</div>
			</div>


			<div id="mail">이메일</div>
			<div>
				<input type="email" class="box" id="userMail" name="userMail"
					placeholder="이메일">&nbsp;@ <select class="mailbox"
					name="mailAdd">
					<option value="">직접입력</option>
					<option value="@naver.com">naver.com</option>
					<option value="@gmail.com">gmail.com</option>
					<option value="@daum.net">daum.net</option>
				</select>
			</div>


			<div>
				<div id="signUp">
					<a href="logIn" style="text-decoration: none; color: #5191ce;">로그인</a>
					<input type="button" class="button" name="userpass" value="가입하기" onClick="joinInfo()">
				</div>
			</div>

		</div>
	</div>

</body>
</html>