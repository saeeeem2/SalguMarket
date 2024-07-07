package com.SalGuMarket.www.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.SalGuMarket.www.domain.FileVO;
import com.SalGuMarket.www.domain.PagingVO;
import com.SalGuMarket.www.domain.SellBoardDTO;
import com.SalGuMarket.www.domain.SellBoardVO;
import com.SalGuMarket.www.repository.FileMapper;
import com.SalGuMarket.www.repository.MemberMapper;
import com.SalGuMarket.www.repository.SellBoardMapper;
import com.SalGuMarket.www.security.MemberVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class SellBoardServiceImpl implements SellBoardService{
	
	private final SellBoardMapper sellBoardMapper;
	private final MemberMapper memberMapper;
	private final FileMapper fileMapper;
	
	@Override
	public List<SellBoardVO> sellBoardList(PagingVO pgvo) {
		return sellBoardMapper.list(pgvo);
	}

	@Override
	public int getTotalCount(PagingVO pgvo) {
		return sellBoardMapper.getTotalCount(pgvo);
	}
	
	@Override
	public List<FileVO> get8MainImage(PagingVO pgvo) {
		return fileMapper.get8MainImage(pgvo);
	}
	
	@Override
	public MemberVO getSellerNickName(String sellerEmail) {
		return memberMapper.selectEmail(sellerEmail);
	}

	@Override
	public SellBoardVO selectOne(long sbno) {
		SellBoardVO sellBoardVO=sellBoardMapper.selectOne(sbno);
		sellBoardVO.setReadCount(sellBoardMapper.readCountUp(sbno));
		return sellBoardVO;
	}

	@Override
	public void modify(SellBoardDTO sellBoardDTO) {
//		log.info(">>>>>>>>>>>>>>"+sellBoardDTO.getSbvo());
//		int isOK = sellBoardMapper.edit(sellBoardDTO.getSbvo());
//		if(isOK > 0 && sellBoardDTO.getFlist().size()>0) {
//			long sbno = sellBoardDTO.getSbvo().getSbno();
//			for(FileVO fvo : sellBoardDTO.getFlist()) {
//				fvo.setSbno(sbno);
//				isOK*=fileMapper.insertFile(fvo);
//			}
//		}
	}
	
	@Override
	@Transactional
	public int saveSell(SellBoardDTO sbDTO) {
		int isOK = sellBoardMapper.insert(sbDTO.getSbvo());
		
		if(isOK > 0) {
			long pno = sellBoardMapper.getRecentSbno();
			// 위 productMapper.saveProduct(pDTO.getPvo()); 구문으로 생성된 Pno이다.
			
			for(FileVO fvo : sbDTO.getFlist1()) {
				fvo.setSbno(pno);
				fvo.setMainImage(1);
				isOK *= fileMapper.saveSellFile(fvo);
			}
			
			for(FileVO fvo : sbDTO.getFlist2()) {
				fvo.setSbno(pno);
				fvo.setMainImage(0);
				isOK *= fileMapper.saveSellFile(fvo);
			}
		}
		
		return isOK;
	}

	@Override
	public int remove(long sbno) {
		return sellBoardMapper.remove(sbno);
	}
	
	@Override
	public long getSbno() {
		return sellBoardMapper.getSbno();
	}
	
	@Override
	public FileVO getMainImageBySbno(Long sbno) {
		return fileMapper.getMainImageBySbno(sbno);
	}

	@Override
	public List<FileVO> getMinorIamgeListBySbno(Long sbno) {
		return fileMapper.getMinorIamgeListBySbno(sbno);
	}
}