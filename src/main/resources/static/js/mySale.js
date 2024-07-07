function spreadMySaleList(email){
    getMySaleListFromServer(email).then(result=>{
        console.log(result);
        const ul = document.getElementById(`mySaleList`);
        if(result.length > 0){
			ul.innerHTML=``; // 초기화
            let li = ``;
            for(let pvo of result){
                let state = `${pvo.state}`;
                switch (state) {
                    case 0: // 판매 중
                        li = `<li class="list-group-item">
                        <span class="badge badge-primary">판매진행</span>
                        <a href="/product/productDetail?pno=${pvo.pno}">${pvo.title}</a>
                        </li>`;
                        break;
                    case 1: // 거래 중
                        li = `<li class="list-group-item">
                        <span class="badge badge-success">입금완료</span>
                        <a href="/product/productDetail?pno=${pvo.pno}">${pvo.title}</a>
                        <button type="button" class="btn sellerCancelButton" data-product-id="${pvo.pno}">거래취소</button>
                        </li>`;
                        break;
                    case 2: // 거래 완료
                        li = `<li class="list-group-item">
                        <span class="badge badge-secondary">거래완료</span>
                        <a href="/product/productDetail?pno=${pvo.pno}">${pvo.title}</a>
                        </li>`;
                        break;
                    case 3: // 구매자의 거래 취소 요청 중
                        li = `<li class="list-group-item">
                        <span class="badge badge-danger">취소진행</span>
                        <a href="/product/productDetail?pno=${pvo.pno}">${pvo.title}</a>
                        <button type="button" class="btn approveCancelButton" data-product-id="${pvo.pno}">취소승인</button>
                        </li>`;
                        break;
                }
                ul.innerHTML += li;
            }
        }else{
            let li = `<li class="list-group-item">My SaleList Empty</li>`;
            ul.innerHTML = li;
        }
    })
}

async function getMySaleListFromServer(email){
    try {
        const resp = await fetch("/product/saleList/"+email);
        const result = await resp.json();
        return result;
    } catch (error) {
        console.log(error);
    }
}