package com.SalGuMarket.www.service;

import java.util.List;

import com.SalGuMarket.www.domain.NoticeBoardDTO;
import com.SalGuMarket.www.domain.NoticeBoardVO;
import com.SalGuMarket.www.domain.PagingVO;

public interface NoticeBoardService {

	List<NoticeBoardVO> noticeBoardList(PagingVO pgvo);

	int getTotalCount(PagingVO pgvo);

	void register(NoticeBoardDTO noticeBoardDTO);

	NoticeBoardDTO selectOne(long noBno);

	long getNoBno();

	void modify(NoticeBoardDTO noticeBoardDTO);

	int remove(long noBno);



}
