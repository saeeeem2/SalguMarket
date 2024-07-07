package com.SalGuMarket.www.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.SalGuMarket.www.domain.CommentVO;
import com.SalGuMarket.www.domain.PagingVO;
import com.SalGuMarket.www.handler.PagingHandler;
import com.SalGuMarket.www.repository.BoardMapper;
import com.SalGuMarket.www.repository.CommentMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService{
	
	private final CommentMapper commentMapper;
	private final BoardMapper boardMapper;
	@Override
	public int post(CommentVO cvo) {
		return commentMapper.insert(cvo);
	}
	@Override
	public PagingHandler getList(long bno, PagingVO pgvo) {
		int totalCount=commentMapper.selectOneBnoTotalCount(bno);
		List<CommentVO> list = commentMapper.getList(bno,pgvo);
		PagingHandler ph=new PagingHandler(pgvo,totalCount,list);
		return ph;
	}
	@Override
	public int edit(CommentVO cvo) {
		return commentMapper.edit(cvo);
	}
	@Override
	public int remove(long cno) {
		// TODO Auto-generated method stub
		return commentMapper.delete(cno);
	}
	@Override
	public int cmtCount(long bno) {
		// TODO Auto-generated method stub
		return commentMapper.selectOneBnoTotalCount(bno);
	}

}
