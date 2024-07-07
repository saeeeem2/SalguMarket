package com.SalGuMarket.www.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.SalGuMarket.www.domain.PagingVO;
import com.SalGuMarket.www.domain.SellBoardVO;

@Mapper
public interface SellBoardMapper {

	List<SellBoardVO> list(PagingVO pgvo);

	int getTotalCount(PagingVO pgvo);

	int insert(SellBoardVO sbvo);

	long getSbno();

	SellBoardVO selectOne(long sbno);

	int readCountUp(long sbno);

	int edit(SellBoardVO sbvo);

	int remove(long sbno);

	Long getRecentSbno();
}