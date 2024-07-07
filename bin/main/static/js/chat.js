function enterRoom(socket) {
    let enterMsg = {
        "type": "ENTER",
        "roomId": [[${room.roomId}]],
        "sender": "chee",
        "msg": ""
    };
    socket.send(JSON.stringify(enterMsg));
}

let socket = new WebSocket("ws://localhost:8088/ws/chat");

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

socket.onmessage = function (e) {
    console.log(e.data);
    let msgArea = document.querySelector('.msgArea');
    let newMsg = document.createElement('div');
    newMsg.innerText = e.data;
    msgArea.append(newMsg);
};

function sendMsg() {
    let content = document.querySelector('.content').value;
    let talkMsg = {
        "type": "TALK",
        "roomId": [[${room.roomId}]],
        "sender": "chee",
        "msg": content
    };
    socket.send(JSON.stringify(talkMsg));
}

function quit() {
    let quitMsg = {
        "type": "QUIT",
        "roomId": [[${room.roomId}]],
        "sender": "chee",
        "msg": ""
    };
    socket.send(JSON.stringify(quitMsg));
    socket.close();
    location.href = "/chat/chatList";
}