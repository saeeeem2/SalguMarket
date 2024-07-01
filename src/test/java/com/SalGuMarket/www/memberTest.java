package com.SalGuMarket.www;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.SalGuMarket.www.repository.MemberMapper;
import com.SalGuMarket.www.security.MemberVO;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SalGuMarketApplication.class)
public class memberTest {
	@Autowired
	private MemberMapper mapper;
	
	@Test
	void contextLoads() {
		for(int i=0; i<15; i++) {			
			MemberVO mvo= MemberVO.builder().email("tester"+i)
					.pwd("a"+i)
					.nickName("a"+i)
					.report(i)
					.build();
			mapper.insert(mvo);
			mapper.insertAuthinit(mvo.getEmail());
		}
		
	}

}
