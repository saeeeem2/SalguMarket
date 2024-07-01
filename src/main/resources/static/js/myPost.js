function spreadMyPostList(email, page=1){
    getMyPostListFromServer(email, page).then(result=>{
        console.log(result);
        //댓글 뿌리기
        const ul = document.getElementById(`myPostList`);
        if(result.boardList.length>0){
            // 댓글을 다시 뿌릴 때 기존 값을 삭제 x , 1page일 경우만 삭제
            if(page==1){ //1페이지에서만 댓글 내용 지우기
				ul.innerHTML=``;
            }
            for(let bvo of result.boardList){
                let li = `<li class="list-group-item" data-cno="${bvo.bno}" data-writer="${bvo.nickName}"><a href="/board/boardDetail?bno=${bvo.bno}">${bvo.title}</a></li>`;
                ul.innerHTML += li;
            }
            let moreBtn = document.getElementById(`moreBtn`);
            //현재 페이지 번호가 전체 페이지 번호보다 작다면
            //아직 나와야 할 페이지가 더 있다면..........
            console.log(moreBtn);
            //more버튼 표시 조건
            if(result.pgvo.pageNo < result.endPage){
                moreBtn.style.visibility = `visible`;
                moreBtn.dataset.page = page+1;
            }else{
                moreBtn.style.visibility = `hidden`;
            }
        }else{
            let li = `<li class="list-group-item">My Post List Empty</li>`;
            ul.innerHTML = li;
        }
    })
}

async function getMyPostListFromServer(email, page){
    try {
        const resp = await fetch("/member/"+email+"/"+page);
        const result = await resp.json();
        return result;
    } catch (error) {
        console.log(error);
    }
}

document.getElementById(`moreBtn`).addEventListener(`click`,(e)=>{
	spreadMyPostList(email, parseInt(e.target.dataset.page));
})