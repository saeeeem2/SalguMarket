package com.SalGuMarket.www.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessage {
	
	public enum MessageType {
		ENTER, TALK, QUIT
	}
	private MessageType type; // 메시지 타입
	
	private long chatCno; // 채팅번호
    private long chatBno; // 방번호
    private String senderNick; // 메시지 보낸사람
    private String chatContent; // 메시지
    private String regAt; // 작성일
    
    // sender 가 가지고있는 email 과
    // ENTER 값의 email 이 서로 다를경우에만
    // 차감되게?
    //private int readCount = 2; // 1:1 채팅이기때문에 기본값 2
    
}