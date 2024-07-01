package com.SalGuMarket.www.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.SalGuMarket.www.domain.BoardDTO;
import com.SalGuMarket.www.domain.BoardVO;
import com.SalGuMarket.www.domain.FileVO;
import com.SalGuMarket.www.domain.PagingVO;
import com.SalGuMarket.www.domain.SellBoardDTO;
import com.SalGuMarket.www.domain.SellBoardVO;
import com.SalGuMarket.www.handler.FileHandler;
import com.SalGuMarket.www.handler.PagingHandler;
import com.SalGuMarket.www.security.MemberVO;
import com.SalGuMarket.www.service.BoardService;
import com.SalGuMarket.www.service.CommentService;
import com.SalGuMarket.www.service.MemberService;
import com.SalGuMarket.www.service.SellBoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/sell/*")
public class SellBoardController {

	private final SellBoardService sellBoardService;
	private final MemberService memberService;
	private final CommentService commentService;
	
	private final FileHandler fileHandler;
	 
	@GetMapping("/sellBoardList")
	public void sellBoardList(Model m,PagingVO pgvo) {
		List<SellBoardVO> list=sellBoardService.sellBoardList(pgvo);
		int totalCount=sellBoardService.getTotalCount(pgvo);
		PagingHandler ph = new PagingHandler(pgvo, totalCount);
		for(SellBoardVO sbvo : list) {
			int cmtCount = commentService.cmtCount(sbvo.getSbno());
			sbvo.setCmtCount(cmtCount);
		}
		m.addAttribute("list",list);
		m.addAttribute("ph",ph);
	}
	
	@GetMapping("/sellBoardRegister")
	public void sellBoardRegister(Principal p, Model m) {
		if(p != null) {
			MemberVO mvo = memberService.selectEmail(p.getName());
			m.addAttribute("loginmvo", mvo);
		}else {
			MemberVO mvo = new MemberVO();
			m.addAttribute("loginmvo", mvo);
		}
	}
	
	@PostMapping("/sellBoardRegister")
	public String sellBoardRegister(SellBoardVO sbvo,@RequestParam(name="files", required=false)MultipartFile[] files) {
		List<FileVO> flist = null;
		if(files[0].getSize()>0||files!=null) {
			flist=fileHandler.uploadFile(files);
		}
		sellBoardService.sellBoardRegister(new SellBoardDTO(sbvo,flist));
		long sbno=sellBoardService.getSbno();
		return "redirect:/sellBoard/sellBoardDetail?sbno="+sbno;
	}
	
	@GetMapping({"/sellBoardDetail","/sellBoardModify"})
	public void boardDetail(Principal p, @RequestParam("sbno") long sbno, Model m) {
		SellBoardDTO sbdto=sellBoardService.selectOne(sbno);
		m.addAttribute("sbdto",sbdto);
		if(p != null) {
			MemberVO mvo = memberService.selectEmail(p.getName());
			m.addAttribute("loginmvo", mvo);
			FileVO fvo = memberService.getFile(p.getName());
			m.addAttribute("fvo", fvo);
			int hasHeart = memberService.hasHeart(p.getName(), sbno);
			m.addAttribute("hasHeart", hasHeart);
		}else {
			MemberVO mvo = new MemberVO();
			m.addAttribute("loginmvo", mvo);
		}
	}
	
	@PostMapping("/sellBoardModify")
	public String sellBoardModify(SellBoardVO sbvo, @RequestParam(name="files", required=false)MultipartFile[] files) {
		List<FileVO> flist=null;
		if(files[0].getSize()>0||files!=null) {
			flist=fileHandler.uploadFile(files);
		}
		sellBoardService.modify(new SellBoardDTO(sbvo,flist));
		long sbno=sellBoardService.getSbno();
		return "redirect:/sellBoard/sellBoardDetail?sbno="+sbno;
	}
	
	@GetMapping("/sellBoardRemove")
	public String remove(@RequestParam("sbno") long sbno) {
		int isOk=sellBoardService.remove(sbno);
		return "redirect:/sellBoard/sellBoardList";
	}
}
