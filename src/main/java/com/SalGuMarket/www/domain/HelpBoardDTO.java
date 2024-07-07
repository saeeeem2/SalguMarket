package com.SalGuMarket.www.domain;

import java.util.List;

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
public class HelpBoardDTO {
	
	private HelpBoardVO hbvo;
	private List<FileVO> flist;

}
