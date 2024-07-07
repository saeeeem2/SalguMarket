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
import com.SalGuMarket.www.domain.HelpBoardDTO;
import com.SalGuMarket.www.domain.HelpBoardVO;
import com.SalGuMarket.www.domain.PagingVO;
import com.SalGuMarket.www.handler.FileHandler;
import com.SalGuMarket.www.handler.PagingHandler;
import com.SalGuMarket.www.security.MemberVO;
import com.SalGuMarket.www.service.HelpBoardService;
import com.SalGuMarket.www.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/help/*")
public class HelpBoardController {
	
	//1:1 문의 게시판 컨트롤러
	private final HelpBoardService helpBoardService;
	private final MemberService memberService;
	
	private final FileHandler fileHandler;
	
	@GetMapping("/helpRegister")
	public void helpRegister(Principal p, Model m) {
		if(p != null) {
			MemberVO mvo = memberService.selectEmail(p.getName());
			m.addAttribute("loginmvo", mvo);
		}else {
			MemberVO mvo = new MemberVO();
			m.addAttribute("loginmvo", mvo);
		}
	}
	
	@PostMapping("/helpRegister")
	public String boardRegister(HelpBoardVO hbvo,@RequestParam(name="files", required=false)MultipartFile[] files) {
		List<FileVO> flist = null;
		if(files[0].getSize()>0||files!=null) {
			flist=fileHandler.uploadFile(files);
		}
		helpBoardService.helpBoardRegister(new HelpBoardDTO(hbvo,flist));
		long hbno=helpBoardService.getHbno();
		return "redirect:/help/helpDetail?hbno="+hbno;
	}
	
	@GetMapping("/helpList")
	public void boardList(Principal p, Model m,PagingVO pgvo) {
		if(p != null) {
			MemberVO mvo = memberService.selectEmail(p.getName());
			m.addAttribute("loginmvo", mvo);
			List<HelpBoardVO> list=helpBoardService.boardList(p.getName(), pgvo);
			int totalCount=helpBoardService.getTotalCount(p.getName(), pgvo);
			PagingHandler ph = new PagingHandler(pgvo, totalCount);
			m.addAttribute("list",list);
			m.addAttribute("ph",ph);
		}else {
			MemberVO mvo = new MemberVO();
			m.addAttribute("loginmvo", mvo);
			List<HelpBoardVO> list=helpBoardService.boardList("null", pgvo);
			int totalCount=helpBoardService.getTotalCount("null", pgvo);
			PagingHandler ph = new PagingHandler(pgvo, totalCount);
			m.addAttribute("list",list);
			m.addAttribute("ph",ph);
		}
	}
	
	@GetMapping({"/helpDetail","/helpModify"})
	public void helpDetail(Principal p, @RequestParam("hbno") long hbno, Model m) {
		HelpBoardDTO hbdto=helpBoardService.selectOne(hbno);
		m.addAttribute("hbdto",hbdto);
		if(p != null) {
			MemberVO mvo = memberService.selectEmail(p.getName());
			m.addAttribute("loginmvo", mvo);
		}else {
			MemberVO mvo = new MemberVO();
			m.addAttribute("loginmvo", mvo);
		}
	}
	
	@PostMapping("/helpModify")
	public String modify(HelpBoardVO hbvo, @RequestParam(name="files", required=false) MultipartFile[] files) {
		List<FileVO> flist=null;
		if(files[0].getSize()>0||files!=null) {
			flist=fileHandler.uploadFile(files);
		}
		helpBoardService.modify(new HelpBoardDTO(hbvo,flist));
		long hbno=
				helpBoardService.getHbno();
		return "redirect:/help/helpDetail?hbno="+hbno;
	}
	
	@GetMapping("/helpRemove")
	public String remove(@RequestParam("hbno") long hbno) {
		int isOk=helpBoardService.remove(hbno);
		return "redirect:/help/helpList";
	}
	
	@GetMapping("/helpAnswer")
	public void answer(Principal p, @RequestParam("hbno") long hbno, Model m) {
		m.addAttribute("hbno", hbno);
		if(p != null) {
			MemberVO mvo = memberService.selectEmail(p.getName());
			m.addAttribute("loginmvo", mvo);
		}else {
			MemberVO mvo = new MemberVO();
			m.addAttribute("loginmvo", mvo);
		}
	}

	
	@PostMapping("/helpAnswer")
	public String answer(HelpBoardVO hbvo, @RequestParam("hbno") long hbno, @RequestParam(name="files", required=false)MultipartFile[] files) {
		List<FileVO> flist = null;
		if(files[0].getSize()>0||files!=null) {
			flist=fileHandler.uploadFile(files);
		}
		int isOk=helpBoardService.answer(new HelpBoardDTO(hbvo, flist), hbno);
		return "redirect:/help/helpList";
	}
	
	
}
