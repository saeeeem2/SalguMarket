package com.SalGuMarket.www.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.SalGuMarket.www.domain.BoardVO;
import com.SalGuMarket.www.domain.FileVO;
import com.SalGuMarket.www.domain.HeartVO;
import com.SalGuMarket.www.domain.PagingVO;
import com.SalGuMarket.www.handler.PagingHandler;
import com.SalGuMarket.www.repository.CommentMapper;
import com.SalGuMarket.www.repository.FileMapper;
import com.SalGuMarket.www.repository.MemberMapper;
import com.SalGuMarket.www.security.MemberVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

	private final MemberMapper memberMapper;
	private final FileMapper fileMapper;
	private final CommentMapper commentMapper;

	@Override
	public int insert(MemberVO mvo) {
		// TODO Auto-generated method stub
		int isOK = memberMapper.insert(mvo);
		return memberMapper.insertAuthinit(mvo.getEmail());
	}

	@Override
	public int getTotalCount(PagingVO pgvo) {
		// TODO Auto-generated method stub
		return memberMapper.getTotalCount(pgvo);
	}

	@Override
	public List<MemberVO> getList(PagingVO pgvo) {
		// TODO Auto-generated method stub
		return memberMapper.getList(pgvo);
	}

	@Override
	public int remove(String email) {
		// TODO Auto-generated method stub
		return memberMapper.remove(email);
	}

	@Override
	public int delete(String email) {
		// TODO Auto-generated method stub
		return memberMapper.delete(email);
	}

	@Transactional
	@Override
	public int setProfile(MemberVO mvo) {
		// TODO Auto-generated method stub
		String email = mvo.getEmail();
		if(mvo.getFvo() != null) {
			FileVO fvo = mvo.getFvo();
			fvo.setEmail(email);
			fileMapper.deleteFile(mvo.getEmail());
			fileMapper.insertProfile(fvo);
			commentMapper.updateProfile(mvo.getEmail(), fvo.getFileName());
			commentMapper.yesProfile(mvo.getEmail());
			memberMapper.yesProfile(mvo.getEmail());
		}else {
			//프사 미선택 삭제
			fileMapper.deleteFile(mvo.getEmail());
			memberMapper.noProfile(mvo.getEmail());
			commentMapper.noProfile(mvo.getEmail());
		}
		int profile = memberMapper.getIsProfile(email);
		mvo.setIsProfile(profile);
		return profile;
	}
	
	@Override
	public MemberVO selectEmail(String email) {
		// TODO Auto-generated method stub
		return memberMapper.selectEmail(email);
	}
	
	@Override
	public MemberVO selectNickName(String nickName) {
		// TODO Auto-generated method stub
		return memberMapper.selcetNickName(nickName);
	}

	@Override
	public int checkpw(MemberVO mvo) {
		// TODO Auto-generated method stub
		return memberMapper.checkpw(mvo);
	}

	@Override
	public int updatePWD(MemberVO mvo) {
		// TODO Auto-generated method stub
		return memberMapper.updatePWD(mvo);
	}

	public FileVO getFile(String email) {
		// TODO Auto-generated method stub
		return fileMapper.getFile(email);
	}

	@Transactional
	@Override
	public PagingHandler getBoardList(String email, PagingVO pgvo) {
		// TODO Auto-generated method stub
		int totalCount = memberMapper.selectEmailTotalCount(email);
		List<BoardVO> list = memberMapper.getBoardList(email, pgvo);
		PagingHandler ph = new PagingHandler(pgvo, totalCount, list, 1);
		return ph;
	}

	@Override
	public void insertHeart(HeartVO hvo) {
		// TODO Auto-generated method stub
		memberMapper.insertHeart(hvo);
	}

	@Override
	public List<HeartVO> getHeart(String email) {
		// TODO Auto-generated method stub
		return memberMapper.getHeart(email);
	}

	@Override
	public BoardVO getHeartBoard(long bno, PagingVO pgvo) {
		// TODO Auto-generated method stub
		return memberMapper.getHeartBoard(bno, pgvo);
	}

	@Override
	public int selectBnoTotalCount(String email) {
		// TODO Auto-generated method stub
		return memberMapper.selectBnoTotalCount(email);
	}

	@Override
	public int hasHeart(String email, long bno) {
		// TODO Auto-generated method stub
		HeartVO hasHeart = memberMapper.hasHeart(email, bno);
		if(hasHeart == null) {
			return 0;
		}else {
			return 1;			
		}
	}
	
	@Override
	public int hasHeartSbno(String email, long sbno) {
		// TODO Auto-generated method stub
		HeartVO hasHeart = memberMapper.hasHeart(email, sbno);
		if(hasHeart == null) {
			return 0;
		}else {
			return 1;			
		}
	}

	@Override
	public void delHeart(HeartVO hvo) {
		// TODO Auto-generated method stub
		memberMapper.delHeart(hvo);
	}
	
	@Override
	public void insertHeartSbno(HeartVO hvo) {
		// TODO Auto-generated method stub
		memberMapper.insertHeart(hvo);
	}
	
	@Override
	public void delHeartSbno(HeartVO hvo) {
		// TODO Auto-generated method stub
		memberMapper.delHeart(hvo);
	}
}