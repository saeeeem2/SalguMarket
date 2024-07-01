package com.SalGuMarket.www.service;

import java.util.List;

import com.SalGuMarket.www.domain.PagingVO;
import com.SalGuMarket.www.domain.SellBoardDTO;
import com.SalGuMarket.www.domain.SellBoardVO;

public interface SellBoardService {

	List<SellBoardVO> sellBoardList(PagingVO pgvo);

	int getTotalCount(PagingVO pgvo);

	void sellBoardRegister(SellBoardDTO sellBoardDTO);

	void modify(SellBoardDTO sellBoardDTO);

	long getSbno();

	int remove(long sbno);

	SellBoardDTO selectOne(long sbno);

}
