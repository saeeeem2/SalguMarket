package com.SalGuMarket.www.service;

import java.util.List;

import com.SalGuMarket.www.domain.BoardVO;
import com.SalGuMarket.www.domain.FileVO;
import com.SalGuMarket.www.domain.HeartVO;
import com.SalGuMarket.www.domain.PagingVO;
import com.SalGuMarket.www.handler.PagingHandler;
import com.SalGuMarket.www.security.MemberVO;

public interface MemberService {

	int insert(MemberVO mvo);

	int getTotalCount(PagingVO pgvo);

	List<MemberVO> getList(PagingVO pgvo);

	int remove(String email);

	int delete(String email);

	int setProfile(MemberVO mvo);

	MemberVO selectEmail(String email);

	MemberVO selectNickName(String nickName);


	int checkpw(MemberVO mvo);

	int updatePWD(MemberVO mvo);

	//String getFileName(String email);

	FileVO getFile(String email);

	PagingHandler getBoardList(String email, PagingVO pgvo);

	void insertHeart(HeartVO hvo);

	List<HeartVO> getHeart(String email);

	BoardVO getHeartBoard(long bno, PagingVO pgvo);

	int selectBnoTotalCount(String email);

	int hasHeart(String email, long bno);

	void delHeart(HeartVO hvo);

}
