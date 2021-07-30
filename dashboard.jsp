<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="resources/css/dashboard.css" rel="stylesheet" type="text/css" />
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap" rel="stylesheet">
<script src="resources/js/js.js"></script>
<title>메인페이지</title>
<script>
function message(message){
	if(message!= ""){
		alert(message);
	}
}
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

function addFriend(){
	let popup = document.getElementById("pop");
	let back = document.getElementById("background");

	popup.style.display = "block";
	back.style.display="block";
}

function searchBtn(){
	let word = document.getElementsByName("word")[0];
	
	let sendJsonData =[];
	sendJsonData.push({word:word.value});
	let clientData = JSON.stringify(sendJsonData);
	
	//alert(clientData);
	postAjax('/schedule/search', clientData ,'searchResult' , 'application/json');
}

function searchResult(jsonData){

	let space = document.getElementById("space");
	let spaceList = "";
	
	for(i=0; i<jsonData.length; i++){
		spaceList += "<td id='td'><input type='checkbox' id='checkbox' name='invit' value='"+ jsonData[i].userId + "'/>" + "     "+ jsonData[i].userId + "     "+ jsonData[i].userName + "     "+  jsonData[i].userMail + "</td><br>";
	}

	spaceList += "<div id='sendMail'  onClick='askFr()'>친구신청</div>";
	space.innerHTML = spaceList;
}

function askFr(){
	let id = document.getElementsByName("invit");
	
	
	let sendJsonData =[];

	for(i=0; i<id.length; i++){
		if(id[i].checked){
			sendJsonData.push({userId:id[i].value});
		}
	}

	let clientData = JSON.stringify(sendJsonData);
	alert(clientData);
	postAjax('/schedule/askFriend' , clientData, 'getFrienList', 'application/json');
}

 function getFrienList(){

	 
 }
 
//사이트에 초대 
 function newFriend(){
 let friendMail = prompt('초대할 친구의 이메일을 입력하세요');
	 
	 let clientData = "to="+ friendMail;
	 
	 
	 getAjax('/schedule/askMail', clientData, 'resultFriendMail');
 }
 
function resultFriendMail(data){
	alert(data.message);
}
 
 
 function closePopup(){
		let popup = document.getElementById("pop");
		let back = document.getElementById("background");

		popup.style.display = "none";
		back.style.display="none";
 }

</script>
</head>
<body onLoad="message('${message}')">
<div id ="header">
<div id="logo"> O N E </div>
	<div id="inb">
 		<div id="title">${uName }님</div>
 		<div name="logOut" class="logoutbtn" onClick="logOut()">로그아웃</div>
 		<div name="addFriend" class="button" onClick="addFriend()">친구추가</div>
 	</div>
 
</div>

	<input type="hidden" value="${userId }" name="userId" />
	<input type="hidden" value="${browser }" name="browser" />
	<!--addObject로 가져온 녀석들 -->

	
	<div class="background" id="background" style="display:none">
		<div id="pop" class="pop" style="display:none">
		<div id ='backspace' onClick='closePopup()'>Close</div>
		<div class='askbtn' onClick='newFriend()'>새친구 초대하기</div>	
		<div id="poplogo"> O N E </div>	
			<input type="text" name="word" id="text" placeholder="친구의 이름을 입력하세요.">
			<div id="searchbtn" class="sbutton" onClick="searchBtn()">검색</div>
			<table>
			<tr><th>아이디</th><th>이름</th><th>이메일</th></tr>
			<td id="space"></td>
			</table>
			
			
		</div>
	</div>
	
	<div id=buttonFrame>
		<a href ="scheduleManage"><div name="teamManage" id=button class="schedulebutton" >스케줄 관리</div></a>
		<a href="teamManage"><div name="schedule" id=button class="teambutton" >팀 관리</div></a>
	</div>
	
</body>
</html>