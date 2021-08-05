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
<script type= "text/javascript">
const message = "${message}";

	if(message!= ""){
		alert(message);
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
	//let privateIp = document.getElementsByName("privateIp")[0];
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
		spaceList += "<input type='checkbox' id='checkbox' name='invit' value='"+ jsonData[i].userId + "'/>" + "<div class='contentsSpaceId'>"+ jsonData[i].userId + "</div><div class='contentsSpaceName'>"+ jsonData[i].userName + "</div><div class='contentsSpaceMail'>"+  jsonData[i].userMail + "</div><br>";
	}

	spaceList += "<div id='sendMail' onClick='askFr()'>친구신청</div>";
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
		
		let pop = document.getElementById("popup");
		let backA = document.getElementById("back");
		
		pop.style.display ="none";
		backA.style.display ="none";
		popup.style.display = "none";
		back.style.display="none";
 }
 
 
 function newSchedule(){
		let popup = document.getElementById("popup");
		let back = document.getElementById("back");
		let sdSpace = document.getElementById("sdSpace");
		let tCode = document.getElementsByName("teCode")[0].value;
		let tName = document.getElementsByName("tName")[0].value;
		const tCodeA = tCode.split(",");
		const tNameA = tName.split(",");
		
		
		let sdSpace2 ="<table><tr class='tCode_frame'><th scope='row'><span class='sdTitle'>팀선택</span></th><td colspan='2'>";
		
		
		sdSpace2 += "<select class='selTeam' name='tCode'><option label='팀선택'></option>";
		for(list=0; list<tCodeA.length; list++){
			sdSpace2 +="<option value=\""+tCodeA[list]+"\">"+ tNameA[list] +"</option>";
		}
		
		sdSpace2 += "</select></tr><tr class='sdtitle_frame'><th scope='row'><span class='sdTitle'>제목</span></th><td colspan='2'><input type='text' id='subject' name='title'></tr>";	
		sdSpace2 += "<tr class='sdtitle_frame'><th scope='row'><span class='sdTitle'>위치</span></th><td colspan='2'><input type='text' id='subject' name='location'></tr>"
		sdSpace2 += "<tr class='sdtitle_frame'><th scope='row'><span class='sdTitle'>날짜</span></th><td colspan='2'><input type='date' id='subject' name='date'></tr>";
		
		sdSpace2 +="<tr class='sdtitle_frame'><th scope='row'><span class='sdTitle'>공개여부</span></th><td colspan='2'><div><input type='checkbox' checked='on' name='open' value='N' onclick='check1(this)'>비공개<input type='checkbox' name='open' value='O' onclick='check1(this)'>공개</div>";
		sdSpace2 += "<tr class='sdtitle_frame'><th scope='row'><span class='sdTitle'>반복여부</span></th><td colspan='2'><select name='loop'><option value='X'>반복없음</option><option value='Y'>년간반복</option><option value='M'>월간반복</option><option value='W'>주간반복</option></select>";
		sdSpace2 += "<tr class='sdtitle_frame'><th scope='row'><span class='sdTitle'>진행상태</span></th><td colspan='2'><select name='process'><option value='B'>예정</option><option value='C'>완료</option><option value='D'>연기</option><option value='H'>보류</option></select>";
		sdSpace2 += "<tr class='sdtitle_frame'><th scope='row'><span class='sdTitle'>첨부파일</span></th><td colspan='2'><div class='sguid'><input type='file' id='file' name='sdFile' multiple></div></tr>";
		sdSpace2 += "<tr class='sdtitle_frame'><th scope='row'><span class='sdTitle'>내용</span></th><td colspan='2'><div class='sguid_contents'><textarea id='contents' name='contents'></textarea></div></tr>";
		sdSpace2 += "</table>";	
	
		sdSpace.innerHTML = sdSpace2;
		popup.style.display = "block";
		back.style.display="block";
 }
 
 function check1(data){
	 let value = document.getElementsByName("open");
	 
	 for(i=0; i<value.length; i++){
		 if(value[i]!=data){
			 value[i].checked= false;
		 }
	 }
 }
 
 function submitSd(){
	 let tCode = document.getElementsByName("tCode")[0];
	 let title = document.getElementsByName("title")[0];
	 let date = document.getElementsByName("date")[0];
	 let addSd = document.assSd;
		 
	 if(tCode.value==""){
		 alert("팀이름은 필수선택사항입니다.");
		 tCode.focus();
		 return;
	 }
	 
	 if(title.value==""){
		 alert("제목은 필수입력사항입니다.");
		 title.focus();
		 return;
	 }
	 	 
	 if(date.value==""){
		 alert("날짜는 필수체크사항입니다.");
		 date.focus();
		 return;
	 }
	 
	 addSd.submit(); 
 }
</script>
</head>
<body onLoad="getAjax('https://api.ipify.org','format=json','setPublicIp')">
<div id ="header">
<div id="logo"> O N E </div>
		
	<div id="inb">
	<div id="picture"><img class="picture" src="${stickerPath }"></div>
 		<div id="title">${uName }님</div>
 		<div name="logOut" class="logoutbtn" onClick="logOut()">로그아웃</div>
 		<div class="button">쪽지함</div>
 		<div name="addFriend" class="button" onClick="addFriend()">친구추가</div>
 	</div>
 
</div>

	<input type="hidden" value="${userId }" name="userId" />
	<input type="hidden" value="${param.browser }" name="browser" />
	<input type="hidden" value="${param.message }" name="message" />
	<input type="hidden" value="${tCode }" name="teCode" />
	<input type="hidden" value="${tName }" name="tName" />
	
	<!--addObject로 가져온 녀석들 -->

		<div id="picture"><img class="picture" src="${param.picture_0 }"></div>
		<div id="picture"><img class="picture" src="${param.picture_1 }"></div>
		<div id="picture"><img class="picture" src="${param.picture_2 }"></div>
		<div id="picture"><img class="picture" src="${param.picture_3 }"></div>
		<div id="picture"><img class="picture" src="${param.picture_4 }"></div>
	
	<div class="background" id="background" style="display:none">
		<div id="pop" class="pop" style="display:none">
		<div id ='backspace' onClick='closePopup()'>Close</div>
		<div class='askbtn' onClick='newFriend()'>새친구 초대하기</div>	
		<div id="poplogo"> O N E </div>	
			<input type="text" name="word" id="text" placeholder="친구의 이름을 입력하세요.">
			<div id="searchbtn" class="sbutton" onClick="searchBtn()">검색</div>
			<div id="spacetitle">
			<span class="headerspace">아이디</span><span class="headerspace">이름</span><span class="headerspace">이메일</span></div>
			<div id ="space"></div>
					
		</div>
	</div>
	
	<div id=buttonFrame>
		<a href ="scheduleManage"><div name="teamManage" id=button class="schedulebutton" >스케줄 관리</div></a>
		<a href="teamManage"><div name="schedule" id=button class="teambutton" >팀 관리</div></a>
	</div>
	
	
	<div class="button" onClick="newSchedule()">날짜</div>
	<div id="back" style="display:none">
		<div id="popup" style="display:none">
		<div id ='backspace' onClick='closePopup()'>Close</div>
		
		<form action="addSd" method="post" name="assSd" enctype="multipart/form-data" >
			<div id="newlogo"> New Schedule </div>
			<div id="sdSpace"></div>
		
		<div id="askSd" onclick="submitSd()">스케줄 등록</div>
		</form>	
	</div>
</div>
	
</body>
</html>