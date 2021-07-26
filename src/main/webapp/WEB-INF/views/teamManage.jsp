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

	function callTeamList(){
		let sendJsonData =[];
		sendJsonData.push({msId:"dlsdbwjd92"});
		let clientData = JSON.stringify(sendJsonData);
		postAjax('/schedule/teamList', clientData,'getTeamList');
		
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
		alert(clientData);
		
		postAjax('/schedule/addTeam', clientData, 'getTeamList');
	}
	
	function getMemberList(tcode){
		let sendJsonData =[];
		sendJsonData.push({tcode:tcode});
		let clientData = JSON.stringify(sendJsonData);
		postAjax("/schedule/memberList", clientData, "getMember");
	}
	
	function getMember(jsonData){
		let member = document.getElementById("memberList");
		let memberList = "<div>[멤버리스트(" + jsonData.length + ")]<div onClick='addMember()'> +멤버추가</div><br><br></div>";
		
	
		for(i = 0; i < jsonData.length; i++){
			memberList += "<div> " + jsonData[i].msId + " - "+ jsonData[i].msName + " (" +jsonData[i].cgName + ") </div>"; 
		}
		
		member.innerHTML = memberList;
		
	}
	
	function backMain(){
		
		let form = makeForm("logIn","get");
		
		document.body.append(form);
		form.submit();
	}
	
</script>
<title>Team Manage</title>
</head>

<body onLoad="callTeamList()">
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
	
	</div>

</body>
</html>