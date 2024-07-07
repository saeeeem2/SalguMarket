package com.SalGuMarket.www.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.SalGuMarket.www.domain.BoardVO;
import com.SalGuMarket.www.domain.PagingVO;


@Mapper
public interface BoardMapper {

	List<BoardVO> list(PagingVO pgvo);

	int getTotalCount(PagingVO pgvo);

	int insert(BoardVO bvo);

	long getBno();

	BoardVO selectOne(long bno);

	int edit(BoardVO bvo);

	int remove(long bno);

	int readCountUp(long bno);



}