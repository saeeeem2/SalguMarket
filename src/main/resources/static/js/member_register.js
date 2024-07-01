console.log("member register in");

document.getElementById('re_btn').disabled=true;

let isEmail = true;
let isNick = true;

/*이메일 중복 확인*/
document.getElementById('email_btn').addEventListener('click', () => {
	let email = document.getElementById('email').value;
	console.log(email);

	checkEmail(email).then(result => {
		console.log(result);
		if (result === '0') {
			alert("사용할 수 없는 이메일입니다.");
			document.getElementById('re_btn').disabled = true;
		} else if (email == '' || email == null) {
			alert('이메일을 입력해주세요.');
			document.getElementById('re_btn').disabled = true;
		} else {
			alert("사용 가능한 이메일입니다.");
			document.getElementById('re_btn').disabled = true;
			isEmail=false;
		}
	})
});

async function checkEmail(email){
	try{
		const url = "/member/checkEmail/" + email;
        const resp = await fetch(url);
        const result = await resp.text();
        if(result==='0'){
			
		}
        return result;
	}catch(error){
		console.log(error);
	}
}

/*닉네임 중복 확인*/
document.getElementById('nickName_btn').addEventListener('click', () => {
	let nickName = document.getElementById('nickName').value;
	console.log(nickName);

	checkNickname(nickName).then(result => {
		console.log(result);
		if (result === '0') {
			alert("사용할 수 없는 닉네임입니다.");
			document.getElementById('re_btn').disabled = true;
		} else if (nickName == '' || nickName == null) {
			alert('닉네임을 입력해주세요.');
			document.getElementById('re_btn').disabled = true;
		} else {
			alert("사용 가능한 닉네임입니다.");
			document.getElementById('re_btn').disabled = true;
			isNick=false;
		}
	})
});

async function checkNickname(nickName){
	try{
		const url = "/member/checkNick/" + nickName;
        const resp = await fetch(url);
        const result = await resp.text();
        return result;
	}catch(error){
		console.log(error);
	}
}

/*비밀번호 정규식*/
document.getElementById('pwd1').addEventListener('input', () => {
    let regpwd = /^(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).{8,15}$/;
    let pwd1 = document.getElementById('pwd1').value;
    let div = document.getElementById('passzone1');
    if (!regpwd.test(pwd1)) {
        div.innerText = '비밀번호가 형식에 맞지 않습니다.';
        document.getElementById('re_btn').disabled=true;
        document.getElementById('pwd2').disabled=true;
    }else{
		div.innerText='';
        document.getElementById('pwd2').disabled=false;
	}
});

/*비밀번호 일치*/
document.getElementById('pwd2').addEventListener('input',()=>{
	let pwd1 = document.getElementById('pwd1').value;
	let pwd2 = document.getElementById('pwd2').value;
	console.log(pwd1);
	console.log(pwd2);
	let div = document.getElementById('passzone2');
	if(pwd1 === pwd2){
		div.innerText="비밀번호가 일치합니다.";
		document.getElementById('re_btn').disabled=false;
	}else{
		div.innerText="비밀번호가 일치하지 않습니다.";
		document.getElementById('re_btn').disabled=true;
	}
	
});

/*모든 값 입력 실패 시 return*/
document.getElementById('re_btn').addEventListener('click',()=>{
		if (isEmail ==true && !isNick==true) {
			alert('중복확인이 필요합니다.');
			return false;
		}else if (!(isEmail ==true && !isNick==true)) {
			document.getElementById('myForm').submit();
		}
});