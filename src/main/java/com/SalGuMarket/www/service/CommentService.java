package com.SalGuMarket.www.service;

import com.SalGuMarket.www.domain.CommentVO;
import com.SalGuMarket.www.domain.PagingVO;
import com.SalGuMarket.www.handler.PagingHandler;

public interface CommentService {

	int post(CommentVO cvo);

	PagingHandler getList(long bno, PagingVO pgvo);

	int edit(CommentVO cvo);

	int remove(long cno);

	int cmtCount(long bno);

}
