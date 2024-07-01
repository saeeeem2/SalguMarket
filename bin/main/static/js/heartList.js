function spreadHeartList(email, page=1){
    getHeartListFromServer(email, page).then(result=>{
        console.log(result);
        const ul = document.getElementById(`favoriteItems`);
        if(result.boardList.length>0){
            if(page==1){
				ul.innerHTML=``;
            }
            for(let bvo of result.boardList){
                let li = `<li class="list-group-item"><a href="/board/boardDetail?bno=${bvo.bno}">${bvo.title}</a></li>`;
                ul.innerHTML += li;
            }
            let moreBtn = document.getElementById(`moreBtnH`);
            console.log(moreBtn);
            if(result.pgvo.pageNo < result.endPage){
                moreBtn.style.visibility = `visible`;
                moreBtn.dataset.page = page+1;
            }else{
                moreBtn.style.visibility = `hidden`;
            }
        }else{
            let li = `<li class="list-group-item">찜목록이 비었습니다.</li>`;
            ul.innerHTML = li;
        }
    })
}

async function getHeartListFromServer(email, page){
    try {
        const resp = await fetch("/member/heart/"+email+"/"+page);
        const result = await resp.json();
        return result;
    } catch (error) {
        console.log(error);
    }
}

document.getElementById(`moreBtnH`).addEventListener(`click`,(e)=>{
	spreadHeartList(email, parseInt(e.target.dataset.page));
})