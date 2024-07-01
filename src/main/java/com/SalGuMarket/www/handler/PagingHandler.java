package com.SalGuMarket.www.handler;

import java.util.List;

import com.SalGuMarket.www.domain.BoardVO;
import com.SalGuMarket.www.domain.CommentVO;
import com.SalGuMarket.www.domain.PagingVO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PagingHandler {

	private int startPage; // 페이지네이션의 시작 번호 => 1, 11, 21...
	private int endPage; // 페이지네이션의 끝 번호 => 10, 20, 30...
	private boolean prev, next; // 페이지네이션 이전&다음 버튼의 활성화 여부
	
	private int totalCount; // 매개변수로 setting 시켜야 함
	private PagingVO pgvo; // 매개변수로 setting 시켜야 함
	
	private List<CommentVO> cmtList;
	// 댓글 더보기 버튼을 위한 변수 => 매개변수로 setting 시켜야 함
	
	private List<BoardVO> boardList;
	
	// 생성자에서 모든 계산 Logic을 완료시켜서 변수에 값들이 세팅되어야 함
	public PagingHandler(PagingVO pgvo, int totalCount) {
		this.pgvo = pgvo;
		this.totalCount = totalCount;
		
//		this.endPage = (int)Math.ceil(pgvo.getPageNo() / (double)pgvo.getQty()) * 10;
		this.endPage = (int)Math.ceil(pgvo.getPageNo() / 10.0) * 10;
		this.startPage = endPage - 9;
		
		// 실제 마지막 페이지
		int realEndPage = (int)Math.ceil(totalCount / (double)pgvo.getQty());
		
		if(realEndPage < endPage) {
			this.endPage = realEndPage;
		}
		
		this.prev = this.startPage > 1;
		this.next = this.endPage < realEndPage;
	}
	
	//memberList 페이징네이션
	public PagingHandler(PagingVO pgvo, int totalCount, int qty) {
		this.pgvo = new PagingVO(pgvo.getPageNo(), qty);
		this.totalCount = totalCount;
		
		this.endPage = (int)Math.ceil(pgvo.getPageNo() / 10.0) * 10;
		this.startPage = endPage - 9;
		
		// 실제 마지막 페이지
		int realEndPage = (int)Math.ceil(totalCount / (double)pgvo.getQty());
		
		if(realEndPage < endPage) {
			this.endPage = realEndPage;
		}
		
		this.prev = this.startPage > 1;
		this.next = this.endPage < realEndPage;
	}
	
	// 댓글 더보기 버튼에 사용되는 생성자 (cmtList를 세팅할 수 있는 생성자)
	public PagingHandler(PagingVO pgvo, int totalCount, List<CommentVO> cmtList) {
		this(pgvo, totalCount);
		this.cmtList = cmtList;
	}
	
	public PagingHandler(PagingVO pgvo, int totalCount, List<BoardVO> boardList, int i) {
		this(pgvo, totalCount);
		this.boardList = boardList;
	}
}
