package com.SalGuMarket.www.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CommentVO {
	
	private int cno, bno, isProfile;
	private String nickName,content,regAt,modAt,email,fileName;
}
