package com.SalGuMarket.www.controller;

import java.io.File;
import java.security.Principal;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.SalGuMarket.www.domain.FileVO;
import com.SalGuMarket.www.domain.PagingVO;
import com.SalGuMarket.www.domain.SellBoardDTO;
import com.SalGuMarket.www.domain.SellBoardVO;
import com.SalGuMarket.www.handler.FileHandler;
import com.SalGuMarket.www.handler.PagingHandler;
import com.SalGuMarket.www.security.MemberVO;
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
	private final CommentService commentService;
	private final MemberService memberService;
	private final FileHandler fileHandler;
	
	@GetMapping("/sellDetail")
	public String transferProductDetail(Principal p, @RequestParam("sbno") Long sbno, Model m) {
		SellBoardVO sbvo = sellBoardService.selectOne(sbno);
		
		if(p != null) {
			MemberVO mvo = memberService.selectEmail(p.getName());
			m.addAttribute("loginmvo", mvo);
			FileVO fvo = memberService.getFile(p.getName());
			m.addAttribute("fvo", fvo);
			int hasHeart = memberService.hasHeartSbno(p.getName(), sbno);
			m.addAttribute("hasHeart", hasHeart);
		}else {
			MemberVO mvo = new MemberVO();
			m.addAttribute("loginmvo", mvo);
		}
		
		FileVO mainImage = sellBoardService.getMainImageBySbno(sbno);
		mainImage.setSaveDir(mainImage.getSaveDir().replace(File.separator, "/"));
		
		List<FileVO> minorIamgeList = sellBoardService.getMinorIamgeListBySbno(sbno);
		for(FileVO file : minorIamgeList) {
			file.setSaveDir(mainImage.getSaveDir().replace(File.separator, "/"));
		}
		
		m.addAttribute("sbvo", sbvo);
		m.addAttribute("mainImage", mainImage);
		m.addAttribute("minorIamgeList", minorIamgeList);
		return "/sell/sellDetail";
	}
	
	// ----------------------------------------------------------------------------------------
	
	@GetMapping("/sellList")
	public void sellList(Model m,PagingVO pgvo) {
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
	
	@GetMapping("/sellRegister")
	public void sellRegister() {}
	
	@PostMapping("/sellRegister")
	public String boardRegister(SellBoardVO sbvo, RedirectAttributes re, Authentication authentication,
			@RequestParam(name="files1", required = false) MultipartFile[] fileMain,
			@RequestParam(name="files2", required = false) MultipartFile[] filesMinor) {
		String SellerEmail = authentication.getName();
		sbvo.setEmail(SellerEmail);
		MemberVO mvo = sellBoardService.getSellerNickName(SellerEmail);
		sbvo.setNickName(mvo.getNickName());
		
		List<FileVO> flistMain = null;
		if(fileMain[0].getSize() > 0 || fileMain != null) {
			flistMain = fileHandler.uploadMainIamgeFile(fileMain);
		}
		List<FileVO> flistMinor = null;
		if(filesMinor[0].getSize() > 0 || filesMinor != null) {
			flistMinor = fileHandler.uploadMinorImageFile(filesMinor);
		}
		
		int isOK = sellBoardService.saveSell(new SellBoardDTO(sbvo, flistMain, flistMinor));
		
		re.addFlashAttribute("saveProduct", isOK);
		return "redirect:/";
	}
	
	@GetMapping("/checkAuth")
	@ResponseBody
	public String getAuth(Authentication authentication) {
		if(authentication == null) {
			// 로그인을 안한 상태라면
			return "1";
		}
		return "0";
	}
}
