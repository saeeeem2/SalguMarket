package com.SalGuMarket.www.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SellBoardVO {
	private int readCount, cmtCount;	
	private long sbno, price;
	private String title, email, nickName, content, regAt, modAt, category;
}