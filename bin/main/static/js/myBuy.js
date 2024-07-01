function spreadMyBuyList(email){
    getMyBuyListFromServer(email).then(result=>{
        console.log(result);
        const ul = document.getElementById(`myBuyList`);
        if(result.length > 0){
			ul.innerHTML=``; // 초기화
            let li = ``;
            for(let pvo of result){
                let state = `${pvo.state}`;
                switch (state) {
                    case 1: // 거래 중
                        li = `<li class="list-group-item">
                        <span class="badge badge-success">입금완료</span>
                        <a href="/product/productDetail?pno=${pvo.pno}">${pvo.title}</a>
                        <button type="button" class="btn confirmPurchaseButton" data-product-id="${pvo.pno}">수취확인</button>
                        <button type="button" class="btn requestCancelButton" data-product-id="${pvo.pno}">거래취소</button>
                        </li>`;
                        break;
                    case 2: // 거래 완료
                        li = `<li class="list-group-item">
                        <span class="badge badge-secondary">거래완료</span>
                        <a href="/product/productDetail?pno=${pvo.pno}">${pvo.title}</a>
                        </li>`;
                        break;
                }
                ul.innerHTML += li;
            }
        }else{
            let li = `<li class="list-group-item">My BuyList Empty</li>`;
            ul.innerHTML = li;
        }
    })
}

async function getMyBuyListFromServer(email){
    try {
        const resp = await fetch("/product/buyList/"+email);
        const result = await resp.json();
        return result;
    } catch (error) {
        console.log(error);
    }
}