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

import com.SalGuMarket.www.domain.FileVO;
import com.SalGuMarket.www.domain.NoticeBoardDTO;
import com.SalGuMarket.www.domain.NoticeBoardVO;
import com.SalGuMarket.www.domain.PagingVO;
import com.SalGuMarket.www.handler.FileHandler;
import com.SalGuMarket.www.handler.PagingHandler;
import com.SalGuMarket.www.service.BoardService;
import com.SalGuMarket.www.service.NoticeBoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/notice/*")
public class NoticeBoardController {

	private final NoticeBoardService noticeBoardService;

	private final FileHandler fileHandler;

	
	@GetMapping("/noticeList")
	public void noticeList(Model m,PagingVO pgvo) {
	 List<NoticeBoardVO> list=noticeBoardService.noticeBoardList(pgvo);
	 int totalCount=noticeBoardService.getTotalCount(pgvo);
	 PagingHandler ph = new PagingHandler(pgvo,totalCount);
	 m.addAttribute("list",list);
	 m.addAttribute("ph",ph);
	 
	}
	
	@GetMapping("/noticeRegister")
	public void noticeRegister() {
	}
	
	@PostMapping("/noticeRegister")
	public String noticeRegister(NoticeBoardVO nbvo, @RequestParam(name="files",required = false) MultipartFile[] files) {
	 List<FileVO> flist=null;
	 if(files[0].getSize()>0||files!=null) {
		 flist=fileHandler.uploadFile(files);
	 }
	 noticeBoardService.register(new NoticeBoardDTO(nbvo,flist));
	 long noBno=noticeBoardService.getNoBno();
	 return "redirect:/notice/noticeDetail?noBno="+noBno;
	}

	@GetMapping({"/noticeDetail","/noticeModify"})
	public void noticeDetail(@RequestParam("noBno")long noBno,Model m) {
		NoticeBoardDTO nbdto=noticeBoardService.selectOne(noBno);
		m.addAttribute("nbdto",nbdto);
	}


	@PostMapping("/noticeModify")
	public String modify(NoticeBoardVO nbvo, @RequestParam(name="files", required = false)MultipartFile[] files) {
		List<FileVO> flist = null;
		if(files[0].getSize()>0||files != null) {
			flist = fileHandler.uploadFile(files);
		}
		noticeBoardService.modify(new NoticeBoardDTO(nbvo,flist));
		long noBno=noticeBoardService.getNoBno();
		return "redirect:/notice/noticeDetail?noBno="+nbvo.getNoBno();
	}
	
	@GetMapping("/noticeRemove")
	public String remove(@RequestParam("noBno") long noBno) {
		int isOK = noticeBoardService.remove(noBno);
		return "redirect:/notice/noticeList";
	}
}
