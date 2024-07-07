package com.SalGuMarket.www.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.SalGuMarket.www.domain.FileVO;
import com.SalGuMarket.www.domain.NoticeBoardDTO;
import com.SalGuMarket.www.domain.NoticeBoardVO;
import com.SalGuMarket.www.domain.PagingVO;
import com.SalGuMarket.www.repository.FileMapper;
import com.SalGuMarket.www.repository.NoticeBoardMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoticeBoardServiceImpl implements NoticeBoardService{
	
	private final NoticeBoardMapper noticeBoardMapper;
	
	private final FileMapper fileMapper;

	@Override
	public List<NoticeBoardVO> noticeBoardList(PagingVO pgvo) {
		return noticeBoardMapper.list(pgvo);
	}

	@Override
	public int getTotalCount(PagingVO pgvo) {
		return noticeBoardMapper.getTotalCount(pgvo);
	}

	@Transactional
	@Override
	public void register(NoticeBoardDTO noticeBoardDTO) {
		int isOk=noticeBoardMapper.insert(noticeBoardDTO.getNbvo());
		if(isOk>0&&noticeBoardDTO.getFlist().size()>0) {
			long noBno=noticeBoardMapper.getNoBno();
			for(FileVO fvo : noticeBoardDTO.getFlist()) {
				fvo.setNoBno(noBno);
				isOk *= fileMapper.insertFile(fvo);
			}
		}
	}

	@Transactional
	@Override
	public NoticeBoardDTO selectOne(long noBno) {
		NoticeBoardDTO noticeBoardDTO = new NoticeBoardDTO();
		noticeBoardDTO.setNbvo(noticeBoardMapper.selectOne(noBno));
		noticeBoardDTO.setFlist(fileMapper.getNoticeFileList(noBno));
		noticeBoardDTO.getNbvo().setReadCount(noticeBoardMapper.readCountUp(noBno));
		return noticeBoardDTO;
	}

	@Override
	public long getNoBno() {
		return noticeBoardMapper.getNoBno();
	}

	@Override
	public void modify(NoticeBoardDTO nbdto) {
		int isOK = noticeBoardMapper.edit(nbdto.getNbvo());
		if(isOK > 0 && nbdto.getFlist().size()>0) {
			long bno = nbdto.getNbvo().getNoBno();
			for(FileVO fvo : nbdto.getFlist()) {
				fvo.setBno(bno);
				isOK*=fileMapper.insertFile(fvo);
			}
		}
	}

	@Override
	public int remove(long noBno) {
		return noticeBoardMapper.remove(noBno);
	}
	
	

}
