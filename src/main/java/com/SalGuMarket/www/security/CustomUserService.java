package com.SalGuMarket.www.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.SalGuMarket.www.repository.MemberMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomUserService implements UserDetailsService{
	
	@Autowired
	private MemberMapper memberMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		log.info(">>>>>>>>>>>>>>>>"+username);
		MemberVO mvo = memberMapper.selectEmail(username);
		mvo.setAuthList(memberMapper.selectAuths(username));
		return new AuthMember(mvo);
	}
}
