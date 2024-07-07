package com.SalGuMarket.www.service;

import java.util.Map;

import com.SalGuMarket.www.security.MemberVO;

public interface MailService {

	Map<String, String> check(MemberVO mvo);

}
