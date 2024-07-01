package com.SalGuMarket.www.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.SalGuMarket.www.domain.BoardDTO;
import com.SalGuMarket.www.domain.BoardVO;
import com.SalGuMarket.www.domain.FileVO;
import com.SalGuMarket.www.domain.PagingVO;
import com.SalGuMarket.www.repository.BoardMapper;
import com.SalGuMarket.www.repository.FileMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

	private final BoardMapper boardMapper;
	
	private final FileMapper fileMapper;

	@Override
	public List<BoardVO> boardList(PagingVO pgvo) {
		return boardMapper.list(pgvo);
	}

	@Override
	public int getTotalCount(PagingVO pgvo) {
		return boardMapper.getTotalCount(pgvo);
	}

	@Override
	public void boardRegister(BoardDTO boardDTO) {
		int isOk=boardMapper.insert(boardDTO.getBvo());
		if(isOk>0&&boardDTO.getFlist().size()>0) {
			long bno=boardMapper.getBno();
			for(FileVO fvo : boardDTO.getFlist()) {
				fvo.setBno(bno);
				isOk *= fileMapper.insertFile(fvo);
			}
		}
		
	}

	@Override
	public BoardDTO selectOne(long bno) {
		BoardDTO boardDto=new BoardDTO();
		boardDto.setBvo(boardMapper.selectOne(bno));
		boardDto.setFlist(fileMapper.selectBnoAllFile(bno));
		boardDto.getBvo().setReadCount(boardMapper.readCountUp(bno));
		return boardDto;
	}

	@Override
	public void modify(BoardDTO boardDTO) {
		log.info(">>>>>>>>>>>>>>"+boardDTO.getBvo());
		int isOK = boardMapper.edit(boardDTO.getBvo());
		if(isOK > 0 && boardDTO.getFlist().size()>0) {
			long bno = boardDTO.getBvo().getBno();
			for(FileVO fvo : boardDTO.getFlist()) {
				fvo.setBno(bno);
				isOK*=fileMapper.insertFile(fvo);
			}
		}
	}

	@Override
	public int remove(long bno) {
		return boardMapper.remove(bno);
	}

	@Override
	public long getBno() {
		return boardMapper.getBno();
	}


}
