/**
 * 
 */
let publicIp;

function isIdCheck(word){ //소문자 숫자 특수문자 중 2가지 사용
	const sEng = /[a-z]/;
	const num = /[0-9]/;
	const special = /[!@#$%^&*]/; 
	
	let count=0;
	if(sEng.test(word)){count++;}
	if(num.test(word)){count++;}
	if(special.test(word)){count++;}
	
	return count;
}
//pwd : 8~20자까지 소문자,대문자,숫자,특수문자 3개이상 사용 

function isPasswordCheck(word){
	const sEng = /[a-z]/;
	const bEng = /[A-Z]/;
	const num = /[0-9]/;
	const special = /[!@#$%^&*]/; 
	
	// password가 영문소문자, 영문대문자, 숫자, 특수문자 중 3가지 이상의 문자군을 사용했는지 여부
	let count = 0;
	if(sEng.test(word)){	count++; }
	if(bEng.test(word)){	count++; }
	if(num.test(word)){	count++; }	
	if(special.test(word)){	count++; }
	
	return count;
}

//아이디 -> 패스워드
function sendUserId(){
   let uCode = document.getElementsByName("userId")[0];
   let aCode = document.getElementsByName("userPass")[0];
   let id= document.getElementById("id");
   
   let next = document.getElementsByName("next")[0];
   let button2 = document.getElementById("button2");
   let pass = document.getElementById("pass");
   
   let text = document.getElementById("text");
   let idFor = document.getElementById("idForget");
   
   uCode.style.display = "none";
   aCode.style.display = "block";
   next.style.display = "none";
   button2.style.display = "block";
   text.style.display = "none";
   id.style.display = "none";
   pass.style.display = "block";
   idFor.innerText="비밀번호를 잊으셨나요?";
   
}

function enterId(){
   let uCode = document.getElementsByName("userId")[0];
   let aCode = document.getElementsByName("userPass")[0];
   let id= document.getElementById("id");
   
   let next = document.getElementsByName("next")[0];
   let button2 = document.getElementById("button2");
   let pass = document.getElementById("pass");
   
   let text = document.getElementById("text");
   let idFor = document.getElementById("idForget");

	if(window.event.keyCode==13){
		
	uCode.style.display = "none";
   aCode.style.display = "block";
   next.style.display = "none";
   button2.style.display = "block";
   text.style.display = "none";
   id.style.display = "none";
   pass.style.display = "block";
   idFor.innerText="비밀번호를 잊으셨나요?";
   	
}
   

}

function enterPwd(){
	
	
	const id = document.getElementsByName("userId")[0];
	const pwd =document.getElementsByName("userPass")[0];
	let method = makeInput("hidden" , "method" , 1);
	let pubIp = makeInput("hidden" , "publicIp" , publicIp);
	let privateIp = makeInput("hidden", "privateIp" , location.host);

	if(window.event.keyCode==13){

	let form = makeForm("Access","post");
	
	
	form.appendChild(id);
	form.appendChild(pwd);
	form.appendChild(method);
	form.appendChild(pubIp);
	form.appendChild(privateIp);
	
	document.body.appendChild(form);
	form.submit();
 }
	
}

//로그인
function logInInfo(){
	//alert(publicIp); //publicIp
	//alert(location.host); //privateIp
	
	const id = document.getElementsByName("userId")[0];
	const pwd =document.getElementsByName("userPass")[0];
	let method = makeInput("hidden" , "method" , 1);
	let pubIp = makeInput("hidden" , "publicIp" , publicIp);
	let privateIp = makeInput("hidden", "privateIp" , location.host);

	let form = makeForm("Access","post");
	
	form.appendChild(id);
	form.appendChild(pwd);
	form.appendChild(method);
	form.appendChild(pubIp);
	form.appendChild(privateIp);
	
	document.body.appendChild(form);
	form.submit();
	
}
//회원가입 
function joinInfo(){
	
	const userId = document.getElementsByName("userId")[0];
	const userPwd = document.getElementsByName("userPass")[0];
	const userPwd2 = document.getElementsByName("userPass2")[0];
	const userPhone = document.getElementsByName("userPhone")[0];
	const userName = document.getElementsByName("userName")[0];
	const userMail = document.getElementsByName("userMail")[0];
	const mailAdd = document.getElementsByName("mailAdd")[0];

//아이디 유효성검사
	if(isIdCheck(userId.value)<2){
		userId.value="";
		userId.focus();
		alert("아이디 6~12글자의 숫자+소문자 조합으로 해주세요");
		return;
	}
	
	if(userId.value ==""){
		userId.value="";
		userId.focus();
		return;
	}
	
	//비밀번호 유효성
	if(isPasswordCheck(userPwd)<2){
		userPwd.value="";
		userPwd.focus();
		alert("숫자,영소대문자,특수문자 포함3가지 입력해주세요");
		return;
	}
	
	if((userPwd.value!= userPwd2.value)){
		userPwd2.value="";
		userPwd2.focus();
		return;
	}
	
	//이름 널값 X	
	if(userName.value ==""){
		userName.value="";
		userName.focus();
		return;
	}
	
	
	
	let form = makeForm("signUp","post");
	
	form.appendChild(userId);
	form.appendChild(userPwd);
	form.appendChild(userPhone);
	form.appendChild(userName);
	form.appendChild(userMail);
	form.appendChild(mailAdd);
	
	document.body.appendChild(form);
	form.submit();
	
	
}
//중복체크
function isDup(){
	const checkId = document.getElementsByName("userId")[0];
	
	let form =makeForm("isDup","post")
	
	form.appendChild(checkId);
	
	document.body.appendChild(form);
	form.submit();
	
}


function makeForm(action, method, name=null){
	
	let form = document.createElement("form");
	
	if(name!= null){form.setAttribute("name", name);}
	form.setAttribute("action",action);
	form.setAttribute("method", method);
	
	return form;
		
}

function makeInput(type, name, val){
	
	let input = document.createElement("input");
	
	input.setAttribute("type", type);
	input.setAttribute("name" , name);
	input.setAttribute("value" , val)
	
	return input;
}

function getAjax(jobCode, clientData, fn){
	/*step1 */
	let ajax = new XMLHttpRequest();
	
	//step2
	ajax.onreadystatechange = function(){
		if(ajax.readyState == 4 && ajax.status == 200){
			//step5
			window[fn](JSON.parse(ajax.responseText));//window - 브라우저
		}
	};
	//step3
	if(clientData!= "") {
		jobCode += "?" + clientData;
	}
	ajax.open("GET" , jobCode);
	
	//ajax.setRequestHeader("Context-type" , "application/x-www-form-urlencoded");
	//step4
	ajax.send();
	
}

function postAjax(jobCode, clientData, fn){
	/*step1 */
	let ajax = new XMLHttpRequest();
	
	//step2
	ajax.onreadystatechange = function(){
		if(ajax.readyState == 4 && ajax.status == 200){
			//step5
			window[fn](JSON.parse(ajax.responseText));//window - 브라우저
		}
	};
	//step3
	ajax.open("POST" , jobCode);
	
	//ajax.setRequestHeader("Context-type" , "application/x-www-form-urlencoded");
	//step4
	ajax.send(clientData);	
}

function setPublicIp(data){
	publicIp = data.ip;
	
}