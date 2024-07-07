package com.SalGuMarket.www.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.SalGuMarket.www.domain.FileVO;
import com.SalGuMarket.www.domain.HelpBoardDTO;
import com.SalGuMarket.www.domain.HelpBoardVO;
import com.SalGuMarket.www.domain.PagingVO;
import com.SalGuMarket.www.repository.FileMapper;
import com.SalGuMarket.www.repository.HelpBoardMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class HelpBoardServiceImpl implements HelpBoardService{
	
	private final HelpBoardMapper helpBoardMapper;
	
	private final FileMapper fileMapper;

	@Override
	public List<HelpBoardVO> boardList(String email, PagingVO pgvo) {
		// TODO Auto-generated method stub
		return helpBoardMapper.list(email, pgvo);
	}

	@Override
	public int getTotalCount(String email, PagingVO pgvo) {
		// TODO Auto-generated method stub
		return helpBoardMapper.getTotalCount(email, pgvo);
	}

	@Override
	public HelpBoardDTO selectOne(long hbno) {
		// TODO Auto-generated method stub
		HelpBoardDTO hbdto = new HelpBoardDTO();
		hbdto.setHbvo(helpBoardMapper.selectOne(hbno));
		hbdto.setFlist(fileMapper.getHelpFileList(hbno));
		return hbdto;
	}

	@Transactional
	@Override
	public void helpBoardRegister(HelpBoardDTO helpBoardDTO) {
		// TODO Auto-generated method stub
		int isOk=helpBoardMapper.insert(helpBoardDTO.getHbvo());
		if(isOk>0&&helpBoardDTO.getFlist().size()>0) {
			long bno=helpBoardMapper.getHbno();
			for(FileVO fvo : helpBoardDTO.getFlist()) {
				fvo.setBno(bno);
				isOk *= fileMapper.insertFile(fvo);
			}
		}
		long hbno = helpBoardMapper.getHbno();
		helpBoardMapper.updateHbno2(hbno);
	}

	@Override
	public long getHbno() {
		return helpBoardMapper.getHbno();
	}

	@Override
	public void modify(HelpBoardDTO helpBoardDTO) {
		int isOk=helpBoardMapper.edit(helpBoardDTO.getHbvo());
		if(isOk>0&&helpBoardDTO.getFlist().size()>0) {
			long hbno=helpBoardDTO.getHbvo().getHbno();
			for(FileVO fvo : helpBoardDTO.getFlist()){
				fvo.setBno(hbno);
				isOk*=fileMapper.insertFile(fvo);
			}
		}
		
	}

	@Override
	public int remove(long hbno) {
		return helpBoardMapper.remove(hbno);
	}

	@Override
	public int answer(HelpBoardDTO hbdto, long hbno) {
		int isOk = helpBoardMapper.answer(hbdto.getHbvo(), hbno);
		if(isOk>0&&hbdto.getFlist().size()>0) {
			long bno=helpBoardMapper.getHbno();
			for(FileVO fvo : hbdto.getFlist()) {
				fvo.setBno(bno);
				isOk *= fileMapper.insertFile(fvo);
			}
		}
		return isOk;
	}

}
