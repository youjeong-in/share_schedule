<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="resources/css/teamManage.css" rel="stylesheet" type="text/css" />
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap" rel="stylesheet">
<script src = "resources/js/js.js"></script>
<script type="text/javascript">
      let teCode;

	function callTeamList(userId){
		let sendJsonData =[];
		sendJsonData.push({msId:userId});
		let clientData = JSON.stringify(sendJsonData);
		postAjax('/schedule/teamList', clientData,'getTeamList' , 'application/json');
		
	}
	function getTeamList(jsonData){
		let team = document.getElementById("teamList");
		let teamList = "<div>[팀리스트(" + jsonData.length + ")]<br><br></div>";
		
		for(r = 0; r < jsonData.length; r++){
			teamList += "<span onClick= 'getMemberList(" + jsonData[r].tcode +")'>" +jsonData[r].tname + " ( " + jsonData[r].msId + " )" + "</span><br>";
		}
		
		team.innerHTML = teamList;
	}
	
	function addTeam(){
		let teamName = prompt('팀 이름을 입력해주세요');
		
		let sendJsonData =[];
		sendJsonData.push({tname:teamName});
		let clientData = JSON.stringify(sendJsonData);
		
		
		postAjax('/schedule/addTeam', clientData, 'getTeamList', 'application/json');
	}
	
	function getMemberList(tcode){
		
		let sendJsonData =[];
		sendJsonData.push({tcode:tcode});
		let clientData = JSON.stringify(sendJsonData);
		postAjax('/schedule/memberList', clientData, 'getMember', 'application/json');
		
		closePopup();
	}
	
	function getMember(jsonData){
		teCode = jsonData[0].tcode; //전역변수에 담아줌
		
		let member = document.getElementById("memberList"); 
		let memberList = "<div>[멤버리스트(" + jsonData.length + ")]<div id='addMember' onClick=\"addMember(\'" + '${userId}' +  "\')\"> + 멤버추가 </div><br><br></div>";
		
		for(i = 0; i < jsonData.length; i++){
			memberList += "<div> " + jsonData[i].msId + " - "+ jsonData[i].msName + " (" +jsonData[i].cgName + ") </div>"; 
		}
		
		
		member.innerHTML = memberList;
		
	}
	
	function addMember(id){
		let sendJsonData =[];
		sendJsonData.push({msId:id,tcode:teCode});
		let clientData = JSON.stringify(sendJsonData);
		
		postAjax('/schedule/friendsList' , clientData, 'friendsList', 'application/json');
	}
	
	function friendsList(jsonData){
		let popup = document.getElementById("popup");
		
		let friend = document.getElementById("friendList");
		let fList = "<div>[친구 리스트(" + jsonData.length + ")]<div><div id ='backspace' onClick='closePopup()'>뒤로가기</div><br><br>";
	
		
		for(i=0; i<jsonData.length; i++){
			fList += "<div><input type='checkbox' name='tdetails' value='"+jsonData[i].msId+"'><input type='hidden' name='email' value='"+jsonData[i].email+"'>" + jsonData[i].msId + " - " + jsonData[i].msName + "</div>";
		}
		
		fList+= "<br><div id='sendMail' onClick='checkFList()'>메일전송</div>";
		friend.innerHTML = fList;
		popup.style.display = "block";
		
	}
	
	function checkFList(){
		let sendJsonData =[];
		let tdetails = [];
		
		let email = document.getElementsByName("email");
		sendJsonData.push({tcode:teCode,tdetails});
		
		let values = document.getElementsByName("tdetails");
	

		for(i=0; i<values.length; i++){
			if(values[i].checked){
				tdetails.push({msId:values[i].value,email:email[i].value});
				
			}
		}
		let clientData = JSON.stringify(sendJsonData);
		alert(clientData);
		
		postAjax('/schedule/sendMail' , clientData, 'getMemberList', 'application/json');
		
		
	}
	
	function closePopup(){
		let popup = document.getElementById("popup");
		
		popup.style.display = "none";
	}
	
	function backMain(){
		
		let form = makeForm("logIn","get");
		
		document.body.append(form);
		form.submit();
	}
	
</script>
<title>Team Manage</title>
</head>

<body onLoad="callTeamList('${userId}')">
<div id ="header">
<div id="logo" onClick="backMain()"> O N E </div>
	<div id="inb">
		<div id="inner">
	  		<div class = "cate" onClick="addTeam()"><a>새 팀 등록 </a></div>
 			<div class = "cate"><a>팀 관리</a></div>
 			<div class = "cate"><a>멤버 관리</a></div>
 		</div>
 	</div>
</div>

<div id ="main" >
	<div id="title">
	<div id="teamList"></div>
	
	<div id = "memberList"></div></div>
	
	<div id = "popup">
	<div id = "friendList"></div></div>
	
	</div>
	
</body>
</html>