package com.SalGuMarket.www.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.SalGuMarket.www.domain.NoticeBoardVO;
import com.SalGuMarket.www.domain.PagingVO;

@Mapper
public interface NoticeBoardMapper {

	List<NoticeBoardVO> list(PagingVO pgvo);

	int getTotalCount(PagingVO pgvo);

	long getNoBno();

	int insert(NoticeBoardVO nbvo);

	NoticeBoardVO selectOne(long noBno);

	int edit(NoticeBoardVO nbvo);

	int remove(long noBno);

	int readCountUp(long noBno);

}
