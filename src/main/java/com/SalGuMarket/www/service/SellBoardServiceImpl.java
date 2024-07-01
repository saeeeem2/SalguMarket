package com.SalGuMarket.www.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.SalGuMarket.www.domain.BoardDTO;
import com.SalGuMarket.www.domain.BoardVO;
import com.SalGuMarket.www.domain.FileVO;
import com.SalGuMarket.www.domain.PagingVO;
import com.SalGuMarket.www.domain.SellBoardDTO;
import com.SalGuMarket.www.domain.SellBoardVO;
import com.SalGuMarket.www.repository.BoardMapper;
import com.SalGuMarket.www.repository.FileMapper;
import com.SalGuMarket.www.repository.SellBoardMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class SellBoardServiceImpl implements SellBoardService{
	
	private final SellBoardMapper sellBoardMapper;
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
	public void sellBoardRegister(SellBoardDTO sellBoardDTO) {
		int isOk=sellBoardMapper.insert(sellBoardDTO.getSbvo());
		if(isOk>0&&sellBoardDTO.getFlist().size()>0) {
			long sbno=sellBoardMapper.getSbno();
			for(FileVO fvo : sellBoardDTO.getFlist()) {
				fvo.setSbno(sbno);
				isOk *= fileMapper.insertFile(fvo);
			}
		}
		
	}

	@Override
	public SellBoardDTO selectOne(long sbno) {
		SellBoardDTO sellBoardDTO=new SellBoardDTO();
		sellBoardDTO.setSbvo(sellBoardMapper.selectOne(sbno));
		sellBoardDTO.setFlist(fileMapper.selectBnoAllFile(sbno));
		sellBoardDTO.getSbvo().setReadCount(sellBoardMapper.readCountUp(sbno));
		return sellBoardDTO;
	}

	@Override
	public void modify(SellBoardDTO sellBoardDTO) {
		log.info(">>>>>>>>>>>>>>"+sellBoardDTO.getSbvo());
		int isOK = sellBoardMapper.edit(sellBoardDTO.getSbvo());
		if(isOK > 0 && sellBoardDTO.getFlist().size()>0) {
			long sbno = sellBoardDTO.getSbvo().getSbno();
			for(FileVO fvo : sellBoardDTO.getFlist()) {
				fvo.setSbno(sbno);
				isOK*=fileMapper.insertFile(fvo);
			}
		}
	}

	@Override
	public int remove(long sbno) {
		return sellBoardMapper.remove(sbno);
	}
	
	@Override
	public long getSbno() {
		return sellBoardMapper.getSbno();
	}
}
