/**
 * 
 */
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
	
	const id = document.getElementsByName("userId")[0];
	const pwd =document.getElementsByName("userpass")[0];
	
	let form = makeForm("Access","post");
	
	form.appendChild(id);
	form.appendChild(pwd);
	
	document.body.appendChild(form);
	form.submit();
	
}

function logInInfo2(){ //같은 name으로 불러올 경우
	
	const id = document.getElementsByName("user")[0];
	const pwd =document.getElementsByName("user")[1];
	
	let form = makeForm("Access2","post");
	
	form.appendChild(id);
	form.appendChild(pwd);
	
	
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