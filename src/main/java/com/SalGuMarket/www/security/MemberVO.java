package com.SalGuMarket.www.security;

import java.util.List;

import com.SalGuMarket.www.domain.FileVO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class MemberVO {
	
	private String email, name, walletAddress, pwd, nickName, regAt, regEmail, lastLogin;
	private int report, heart, isProfile;
	private List<AuthVO> authList;
	private FileVO fvo;
	
	public MemberVO(String email, String walletAddress) {
		this.email = email;
		this.walletAddress = walletAddress;
	}


}