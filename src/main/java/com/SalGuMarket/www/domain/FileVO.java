package com.SalGuMarket.www.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class FileVO {
	private String uuid; //사진id
	private String saveDir; //저장날짜
	private String fileName; //파일이름
	private int fileType; //파일타입
	private int mainImage;
	private String productTitle;
	private long price;
	private long bno; //자유게시판
	private long sbno; //상품
	private long noBno; //공지사항
	private long qBno; //1:1문의
	private long fileSize; //파일 사이즈
	private String regAt; //등록날짜
	private String Email;

}