package com.SalGuMarket.www.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.SalGuMarket.www.domain.HelpBoardDTO;
import com.SalGuMarket.www.domain.HelpBoardVO;
import com.SalGuMarket.www.domain.PagingVO;

@Mapper
public interface HelpBoardMapper {

	List<HelpBoardVO> list(@Param("email")String email, @Param("pgvo")PagingVO pgvo);

	int getTotalCount(@Param("email")String email, @Param("pgvo")PagingVO pgvo);

	HelpBoardVO selectOne(long hbno);

	long getHbno();

	int insert(HelpBoardVO hbvo);

	int edit(HelpBoardVO hbvo);

	int remove(long hbno);

	void updateHbno2(long hbno);

	int answer(@Param("hbvo")HelpBoardVO hbvo,@Param("hbno") long hbno);

}
