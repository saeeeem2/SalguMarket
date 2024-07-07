package com.SalGuMarket.www.service;

import java.util.List;

import com.SalGuMarket.www.domain.BoardDTO;
import com.SalGuMarket.www.domain.BoardVO;
import com.SalGuMarket.www.domain.PagingVO;

public interface BoardService {

	List<BoardVO> boardList(PagingVO pgvo);

	int getTotalCount(PagingVO pgvo);

	void boardRegister(BoardDTO boardDTO);

	BoardDTO selectOne(long bno);

	void modify(BoardDTO boardDTO);

	int remove(long bno);

	long getBno();


	
}
