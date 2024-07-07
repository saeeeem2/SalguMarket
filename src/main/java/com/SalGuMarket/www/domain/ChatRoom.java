package com.SalGuMarket.www.domain;

import java.util.HashSet;
import java.util.Set;

import org.springframework.web.socket.WebSocketSession;

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
public class ChatRoom {
	
	private long chatBno;
	
	// seller = pno가 가지고있는 email
	private String sellerEmail;
	private String sellerNick;
	// buyer = 현재 로그인되어있는 email
	private String buyerEmail;
	private String buyerNick;

	private String chatName; //	 방제
	
	private String regAt;
	private long pno;
	private long price;
	
}