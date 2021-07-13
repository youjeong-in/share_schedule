/**
 * 
 */
let publicIp;

function sendUserId(){
   let uCode = document.getElementsByName("userId")[0];
   let aCode = document.getElementsByName("userpass")[0];
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

function logInInfo(){
	//alert(publicIp); //publicIp
	//alert(location.host); //privateIp
	
	const id = document.getElementsByName("userId")[0];
	const pwd =document.getElementsByName("userpass")[0];
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