console.log("member find in");
document.getElementById("FID").addEventListener('click', () => {

	let name= document.getElementById("Fname").value;
	let	email= document.getElementById("Femail").value;
		console.log(name,email);
	if (name == null || name == '' || email == null || email == '') {
		alert("모두 입력해주세요.")
		name.focus();
		return false;
	}
	
	FindUser(name, email).then(result => {
		if (result.status === "0") {
			alert("해당하는 이름(이메일)이 없습니다.");
			name.focus();
			return false;
		} else if (result.status === "1") {
			alert("인증메일이 전송되었습니다.");
			console.log(result.authkey);
			document.getElementById("FID2").addEventListener("click", () => {
				let authkey = document.getElementById("authkey").value;
				if (authkey == result.authkey) {
					$('#modal').modal('show');
					document.getElementById("modal_text").innerText ="회원님의 아이디는 "+result.email+" 입니다.";
					document.getElementById("modal_confirm").addEventListener("click", () => {
						window.location.href = 'login';
					});
				} else if (authkey == null || authkey == '') {
					alert("인증번호를 입력해주세요.");
					return false;
				} else if (authkey != result.authkey) {
					alert("인증번호가 틀렸습니다. 다시 입력해주세요.");
					return false;
				}
			})
		}
	})

});

async function FindUser(name, email) {
	try {
		const url = "/member/findid/" + name + "/" + email;
		const resp = await fetch(url);
		const result = await resp.json();
		return result;
	} catch (error) {
		console.log(error);
	}
}

