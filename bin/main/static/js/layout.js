console.log("layout.js Join Success");

document.getElementById('productSaleButton').addEventListener('click', async function(event){
    event.preventDefault(); // a태그의 href 속성 실행 방지

    // 사용자의 지갑 주소 유무를 확인하는 서버의 API 요청
    try {
        const response = await fetch('/product/checkWalletAddress');
        const result = await response.text();
        
        if(result === "1") {
            // 지갑 주소가 없는 경우, 모달 창 띄우기
            document.getElementById('staticBackdropButton').click();
        }else if(result === "0"){
            // 지갑 주소가 있는 경우, 판매 등록 페이지로 이동
            window.location.href = '/product/productSale';
        }else if(result === "2"){
			// 로그인을 안한 상태라면...
			window.location.href = '/member/login';
		}
    } catch (error) {
        console.error('>>> Error >>> ', error);
    }
});

// 지갑 주소 등록 모달창 input 태그에 대한 함수
document.getElementById('staticBackdropInput').addEventListener('input', ()=>{
    let staticBackdropInput = document.getElementById('staticBackdropInput');
    let staticBackdropInputWarning = document.getElementById('staticBackdropInputWarning');
    let staticBackdropSubmitButton = document.getElementById('staticBackdropSubmitButton');

    if(staticBackdropInput.value.length < 42 || staticBackdropInput.value.length > 42){
        staticBackdropInputWarning.classList.add('textColorOrange');
        staticBackdropInputWarning.classList.remove('textColorGreen');
        staticBackdropInputWarning.innerHTML = "올바른 지갑 주소를 입력해 주세요.";
        staticBackdropInputWarning.removeAttribute('hidden');

        staticBackdropSubmitButton.disabled = true;
    }else if(staticBackdropInput.value.length == 42){
        staticBackdropInputWarning.classList.add('textColorGreen');
        staticBackdropInputWarning.classList.remove('textColorOrange');
        staticBackdropInputWarning.innerHTML = "올바른 지갑 주소입니다.";
        staticBackdropInputWarning.removeAttribute('hidden');

        staticBackdropSubmitButton.disabled = false;
    }
});