package com.SalGuMarket.www.service;

import java.util.List;

import com.SalGuMarket.www.domain.FileVO;
import com.SalGuMarket.www.domain.PagingVO;
import com.SalGuMarket.www.domain.SellBoardDTO;
import com.SalGuMarket.www.domain.SellBoardVO;
import com.SalGuMarket.www.security.MemberVO;

public interface SellBoardService {

	List<SellBoardVO> sellBoardList(PagingVO pgvo);

	int getTotalCount(PagingVO pgvo);

	void modify(SellBoardDTO sellBoardDTO);

	long getSbno();

	int remove(long sbno);

	SellBoardVO selectOne(long sbno);

	MemberVO getSellerNickName(String sellerEmail);
	
	int saveSell(SellBoardDTO sellBoardDTO);
	
	List<FileVO> get8MainImage(PagingVO pgvo);
	
	FileVO getMainImageBySbno(Long sbno);

	List<FileVO> getMinorIamgeListBySbno(Long sbno);
}