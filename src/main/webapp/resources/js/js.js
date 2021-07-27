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

/* 유효성 검사 */
function isValidateCheck(type, word){
	let result;
	const codeComp = /^[a-zA-Z]{1}[0-9a-zA-Z]{5,11}$/g;
	const pwdComp1 = /[a-z]/g;
	const pwdComp2 = /[0-9]/g;
	const pwdComp3 = /[!@#$%^&*]/g;
	
	if(type == 1){
		result = codeComp.test(word); 
	}else if(type == 2){
		let count = 0;
		count += pwdComp1.test(word)?1:0;
		count += pwdComp2.test(word)?1:0;
		count += pwdComp3.test(word)?1:0;
		
		result = (count >= 3)? true:false;
	}
	
	return result;
}



function korCheck(obj, event){
	const pattern = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/;
	
	if(pattern.test(event.target.value.trim())) {
		obj.value = obj.value.replace(pattern,'').trim();
	}
}

function charCount(value, min, max){
	return value.length >= min && value.length<=max;
	
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
//다음 누르면 패스워드 창으로 넘어감 (enter)
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
//enter누르면 로그인이됨
function enterPwd(){
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
	
	const id = document.getElementsByName("userId")[0];
	const pwd =document.getElementsByName("userPass")[0];
	let method = makeInput("hidden" , "method" , 1);
	let pubIp = makeInput("hidden" , "publicIp" , publicIp);
	let privateIp = makeInput("hidden", "privateIp" , location.host);
	const brow = makeInput("hidden", "browser", result);
	
	if(window.event.keyCode==13){

	let form = makeForm("Access","post");
	
	
	form.appendChild(id);
	form.appendChild(pwd);
	form.appendChild(method);
	form.appendChild(pubIp);
	form.appendChild(privateIp);
	form.appendChild(brow);
	
	document.body.appendChild(form);
	form.submit();
 }
	
}

//로그인
function logInInfo(){
	//alert(publicIp); //publicIp
	//alert(location.host); //privateIp
	
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

	//alert(browser);
	const id = document.getElementsByName("userId")[0];
	const pwd =document.getElementsByName("userPass")[0];
	let method = makeInput("hidden" , "method" , 1);
	let pubIp = makeInput("hidden" , "publicIp" , publicIp);
	let privateIp = makeInput("hidden", "privateIp" , location.host);
	const brow = makeInput("hidden", "browser", result);


	
	let form = makeForm("Access","post");
	
	form.appendChild(id);
	form.appendChild(pwd);
	form.appendChild(method);
	form.appendChild(pubIp);
	form.appendChild(privateIp);
	form.appendChild(brow);
	
	
	document.body.appendChild(form);
	form.submit();
	
}


//회원가입 
function joinInfo(){
	
	const userId = document.getElementsByName("userId")[0];
	const userPwd = document.getElementsByName("userPass")[0];
	const userPhone = document.getElementsByName("userPhone")[0];
	const userName = document.getElementsByName("userName")[0];
	const userMail = document.getElementsByName("userMail")[0];
	const mailAdd = document.getElementsByName("mailAdd")[0];


	//이름 널값 X
	if(userName.value ==""){
		alert("이름은 필수입력사항입니다.");
		userName.value="";
		userName.focus();
		return;
	}
	
	//이름 5글자이상일경우
	if(!charCount(userName.value,2,5)){ 
		alert("이름은 최대 5글자까지만 허용됩니다. 문의는 고객센터로해주세요.");
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

function postAjax(jobCode, clientData, fn, header){
	/* Step 1*/
	let ajax = new XMLHttpRequest();
		
	/* Step2 */
	ajax.onreadystatechange = function(){
		if(ajax.readyState == 4 && ajax.status == 200){
			/* Step 5 */
			
			const jsonData = ajax.responseText;
			//alert(jsonData);
			
			window[fn](JSON.parse(jsonData));
		}
	};
	/* Step 3 */
	ajax.open("POST", jobCode);
	/* Step 4 */
	//ajax.setRequestHeader("Content-type", "application/x-www-form-urlencoded"); // form으로 넘겨줌
	//"application/json"
	ajax.setRequestHeader("Content-type", header); // json으로 넘겨줌
	ajax.send(clientData);
}

function setPublicIp(data){
	publicIp = data.ip;
	
}