package com.SalGuMarket.www.service;

import java.util.List;

import com.SalGuMarket.www.domain.HelpBoardDTO;
import com.SalGuMarket.www.domain.HelpBoardVO;
import com.SalGuMarket.www.domain.PagingVO;

public interface HelpBoardService {

	List<HelpBoardVO> boardList(String email, PagingVO pgvo);

	int getTotalCount(String email, PagingVO pgvo);

	HelpBoardDTO selectOne(long hbno);

	void helpBoardRegister(HelpBoardDTO helpBoardDTO);

	long getHbno();

	void modify(HelpBoardDTO helpBoardDTO);

	int remove(long hbno);

	int answer(HelpBoardDTO hbdto, long hbno);

}