function spreadChatList(chatBno, page=1){
    getChatListFromServer(chatBno).then(result=>{
        console.log(result);
        //댓글 뿌리기
        const ul = document.getElementById(`msgArea`);
        if(result.length>0){
            // 댓글을 다시 뿌릴 때 기존 값을 삭제 x , 1page일 경우만 삭제
            if(page==1){ //1페이지에서만 댓글 내용 지우기
				ul.innerHTML=``;
            }
            for(let chat of result){
				const regAt = new Date(chat.regAt);
                const hour = regAt.getHours().toString().padStart(2, '0');
                const minute = regAt.getMinutes().toString().padStart(2, '0');
                let loginUser = document.getElementById('loginNick').value;
				let currUser = `${chat.senderNick}`;
				console.log(loginUser);
				console.log(currUser);
                if(loginUser != currUser){
                let li = `
                <div class="you1">
                <br>
                <img class="chat-img" src="/" />
	                <div class="you">
	                    <div class="chat-name">${chat.senderNick}</div>
	                    <div class="chat-Content">${chat.chatContent}</div>
	                    <div class="you-time">${hour}:${minute}</div>
	                </div>   
	                <br> 
            	</div>
                `;
                ul.innerHTML += li;
				} else {
				let li = `
				<div class="me1">
               	<br>
	                <div class="me">
	                    <div class="chat-Content">${chat.chatContent}</div>
	                    <div class="me-time">${hour}:${minute}</div>
	                </div>   
	            	<br>
            	</div>
					`;
                ul.innerHTML += li;
				}
            }
        }else{
            let li = `<li class="list-group-item">대화가 없습니다</li>`;
            ul.innerHTML = li;
        }
    })
}

/*
            <div class="you1">
                <img class="chat-img" src="/" />
                <div class="you">
                    <div class="chat-name">내 친구 / ${chat.senderNick}</div>
                    밥 머것냐? / ${chat.chatContent}
                    <div class="you-time">오후 3:40</div>
                </div>
            </div>
            <div class="me1">
                <div class="me">
                    ㅇㅇ
                    <div class="me-time">오후 3:40</div>
                </div>
                <br>
                <div class="me">
                    너는?
                    <div class="me-time">오후 3:41</div>
                </div>
                <br>
            </div>
*/

async function getChatListFromServer(chatBno){
    try {
        const resp = await fetch("/chat/chatRoom/"+chatBno);
        const result = await resp.json();
        return result;
    } catch (error) {
        console.log(error);
    }
}

console.log('ChatRoom JavaScript Insert');
//임시로 값 넣어주기
let userEmail = document.getElementById('email').value;
let userNick = document.getElementById('nickName').value;
console.log(userEmail);
console.log(userNick);

function enterRoom(socket) {
    let enterMsg = {
        type: "ENTER",
        chatBno: chatBno,
        senderEmail: userEmail,
        senderNick: userNick,
        chatContent: ""
    };
    socket.send(JSON.stringify(enterMsg));
    console.log("enterMsg");    
    console.log(enterMsg);
}

let socket = new WebSocket("ws://localhost:8088/ws/chat");
console.log("socket");
console.log(socket);
socket.onopen = function (e) {
    console.log('서버에 연결되었습니다!');
    enterRoom(socket);
};

socket.onclose = function (e) {
    console.log('연결이 종료되었습니다');
};

socket.onerror = function (e) {
    console.log(e);
};

function sendMsg() {
    let content = document.getElementById('content').value;
    console.log(content);
    let talkMsg = {
        type: "TALK",
        chatBno: chatBno,
        senderEmail: userEmail,
        senderNick: userNick,
        chatContent: content
    };
    socket.send(JSON.stringify(talkMsg));
 	//서버로 메시지가 전송되었는지 확인용
    console.log("talkMsg :", talkMsg);
	spreadChatList(chatBno);
};


socket.onmessage = function (e) {
    console.log('WebSocket 메시지 수신:', e);

    let ul = document.getElementById('msgArea'); // 변경된 요소에 대한 참조를 가져옵니다.

    try {
        let message = JSON.parse(e.data);
        console.log('파싱된 메시지:', message);

        let chat = {}; // chat 객체를 생성 새로운 메시지 정보를 저장
        let d = new Date(); // 현재 시간
        let hour = d.getHours();
        let minute = d.getMinutes();
		let loginUser = document.getElementById('loginUser').value;

       if (message.type === 'ENTER' || message.type === 'QUIT') {
            chat.senderNick = ''; // 채팅 닉네임
            chat.chatContent = message.chatContent; // 채팅 내용
        } else if (message.type === 'TALK') {
            chat.senderNick = message.senderNick; // 채팅 발신자의 닉네임
            chat.chatContent = message.chatContent; // 채팅 내용
        }
		if(loginUser != userEmail){
			console.log("다름");
			console.log(loginUser);
        let li = `
            <div class="you1">
                <br>
                <img class="chat-img" src="/" />
                <div class="you">
                    ${chat.chatContent}
                    <div class="you-time">${hour}:${minute}</div>
                </div>   
                <br> 
            </div>
        `;
        ul.innerHTML += li; // 새로운 채팅 메시지를 목록에 추가합니다.
		} else {
		let li = `
			<div class="me1">
               	<br>
	                <div class="me">
	                    <div class="chat-name">${chat.senderNick}</div>
	                    <div class="chat-Content">${chat.chatContent}</div>
	                    <div class="me-time">${hour}:${minute}</div>
	               </div>   
	           <br>
            </div>
		`;
        ul.innerHTML += li; // 새로운 채팅 메시지를 목록에 추가합니다.
		}
    } catch (error) {
        console.error('WebSocket 메시지 처리 중 오류 발생:', error);
    }
};


function quit() {
    let quitMsg = {
        type: "QUIT",
        chatBno: chatBno,
        senderEmail: userEmail,
        senderNick: userNick,
        chatContent: ""
    };
    socket.send(JSON.stringify(quitMsg));
    socket.close();
    location.href = "/chat/chatList";
}

document.getElementById(`sendBtn`).addEventListener(`click`,()=>{
	spreadChatList(chatBno);
	document.getElementById(`content`).value="";
});