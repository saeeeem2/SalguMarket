package com.SalGuMarket.www.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NoticeBoardVO {
	
	private long noBno;
	private int readCount;
	private String title, content, regAt, modAt;

}
