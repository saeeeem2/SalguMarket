package com.SalGuMarket.www.domain;

import lombok.Getter;
import lombok.Setter;

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
public class HelpBoardVO {
	
	private String  title,email,content,regAt,modAt;
	private int hbno, hbno2;
	
}
