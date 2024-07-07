console.log("boardComment js");
moreBtn.style.visibility=`hidden`;

document.getElementById('cmtPostBtn').addEventListener('click',()=>{
    const cmtText=document.getElementById('cmtText');
    if(cmtText.value==null||cmtText.value==''){
        alert('댓글을 입력해주세요.');
        cmtText.focus();
        return false;
    } else {
        let cmtData={
            bno:bnoVal,
            email:document.getElementById('cmtEmail').value,
            nickName:document.getElementById('cmtNickName').value,
            content:cmtText.value,
            isProfile:document.getElementById('cmtIsProfile').value,
            fileName:document.getElementById('cmtProfileImage').value
        };
        console.log(cmtData);
        postCommentToServer(cmtData).then(result=>{
             if(result==`1`){
                alert(`댓글이 등록되었습니다.`);
                cmtText.value=``;
            }
            //화면에 뿌리기
            spreadCommentList(bnoVal);
        })
    }
})

async function postCommentToServer(cmtData){
    try{
        const url=`/comment/post`;
        const config={
            method:`post`,
            headers:{
                'content-type':`application/json; charset=utf-8`
            },
            body:JSON.stringify(cmtData)
        };
        const resp=await fetch(url,config);
        const result=await resp.text();
        return result;
    } catch(error){
        console.log(error);
    }
}

async function getCommentListFromServer(bno,page){
    try {
        const resp=await fetch("/comment/"+bno+"/"+page);
        const result=await resp.json();
        return result;
    } catch (error) {
        console.log(error);
    }
}

function spreadCommentList(bno,page=1){
    getCommentListFromServer(bno,page).then(result=>{
        const ul=document.getElementById(`cmtListArea`);
        if(result.cmtList.length>0){
            if(page==1){
                ul.innerHTML=``;
            }
            for(let cvo of result.cmtList){
                let li=`<li class="list-group-item d-flex justify-content-between align-items-start" data-cno="${cvo.cno}" data-writer="${cvo.nickName}">`;
                li += `<img src="">`;
                li += `<div class="ms-2 me-auto">`;
                if(cvo.isProfile == 0){
                li += `<img id="profile-image-display" class="profile-image-display" alt="" src="/img/salgu_profile.png"><br>`;
				}else{
                li += `<img id="profile-image-display" class="profile-image-display myProfile" alt="" src="/upload/profile/${cvo.nickName}_${cvo.fileName}">`;				
				}
                li += `<div id="cmtWriter" class="fw-bold">${cvo.nickName}<span style="float: right;">${cvo.regAt}</span></div>`;
                li += `${cvo.content}`;
                li += `</div>`;
                if(nick==cvo.nickName){
                li += `<button class="cmtModBtn mod cmtBtn" data-bs-toggle="modal" data-bs-target="#myModal" type="button">수정</button>`;
                li += `<button class="cmtDelBtn del cmtBtn" type="button">삭제</button>`;						
				}
                li += `</li>`;
                ul.innerHTML += li;
            }
            let moreBtn = document.getElementById('moreBtn');
    
            console.log(moreBtn);
            if(result.pgvo.pageNo<result.endPage){
                moreBtn.style.visibility=`visible`;
                moreBtn.dataset.page=page+1;
            }else{
                moreBtn.style.visibility=`hidden`;
            }
        } else {
            let li=`<ul class="list-group list-group-numbered"></ul>`;
            ul.innerHTML = li;
        }
    })
    
} 

document.addEventListener(`click`,(e)=>{
	if(e.target.classList.contains(`mod`)){
		//수정
		let li = e.target.closest(`li`); //내 버튼이 포함되어있는 li 찾기
		//같은 부모의 다음 형제 객체를 반환
		let cmtText = li.querySelector(`.fw-bold`).nextSibling;
		console.log(cmtText);
		//현재 선택한 노드의 밸류 반환
		document.getElementById(`cmtTextMod`).value = cmtText.nodeValue;
		document.getElementById(`cmtModBtn`).setAttribute(`data-cno`, li.dataset.cno);
	}else if(e.target.id == `cmtModBtn`){
		//모달 수정 버튼
		let cmtDataMod = {
		cno : e.target.dataset.cno,
		content : document.getElementById(`cmtTextMod`).value			
		};
		editCommentToServer(cmtDataMod).then(result=>{
			if(result===`1`){
				alert(`댓글이 수정되었습니다.`);
				//모달창 닫기
				spreadCommentList(bnoVal);
				document.querySelector(`.btn-close`).click();
			}
		})
	}else if(e.target.classList.contains(`del`)){
		//삭제
		let li = e.target.closest(`li`);
		let cno = li.dataset.cno;
		removeCommentFromServer(cno).then(result=>{
			if(result===`1`){
				alert(`댓글이 삭제되었습니다.`);
				spreadCommentList(bnoVal);
			}
		})
	}else if(e.target.id==`moreBtn`){
		spreadCommentList(bnoVal, parseInt(e.target.dataset.page));
	}
})

async function editCommentToServer(cmtDataMod){
	try{
		const url="/comment/edit";
		const config={
			method:'put',
			headers:{
				'content-type':'application/json; charset=utf-8'
			},
			body:JSON.stringify(cmtDataMod)
		};
		const resp = await fetch(url, config);
		const result = await resp.text();
		return result;
	}catch(error){
		console.log(error);
	}
}

async function removeCommentFromServer(cno){
	try{
		const url = `/comment/`+cno;
		const config ={
			method : `delete`
		};
		const resp = await fetch(url, config);
		const result = await resp.text();
		return result;
	}catch{
		console.log(error);
	}
}